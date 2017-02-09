package amqo.com.comics.views.list.activity;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.views.BaseComicsPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicsListPresenter
        extends BaseComicsPresenter
        implements ComicsContract.ListPresenter {

    private static final String TAG = ComicsListPresenter.class.getSimpleName();

    protected Consumer<Comics> mComicsConsumer;

    private Comics mLastReceivedComics;

    public ComicsListPresenter(
            ComicsEndpoint comicsEndpoint,
            ComicsContract.ListView comicsView) {

        mComicsView = comicsView;
        mComicsEndpoint = comicsEndpoint;

        initComicsComsumer();
    }

    @Override
    public void loadMoreComics() {

        if (isInLastPage()) return;

        getComicsView().setLoading(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put(ComicsEndpoint.OFFSET_PARAMETER, getNextOffset());

        Observable<Comics> comicsObservable = mComicsEndpoint.getComics(
                CHARACTER_ID, parameters);
        doSubscribe(comicsObservable);
    }

    @Override
    public void refreshComics() {

        ((ComicsContract.ListView)mComicsView).setLoading(true);

        getComicsView().clearComics();

        Observable<Comics> comicsObservable = mComicsEndpoint.getComics(
                CHARACTER_ID, new HashMap<String, String>());
        doSubscribe(comicsObservable);
    }

    protected void initComicsComsumer() {

        mComicsConsumer = new Consumer<Comics>() {
            @Override
            public void accept(Comics comics) throws Exception {
                if (comics == null || !comics.isResultOk()) return;
                getComicsView().onComicsLoaded(comics);
                mLastReceivedComics = comics;
            }
        };
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public String getNextOffset() {
        if (mLastReceivedComics == null) return "0";
        return mLastReceivedComics.getNextOffset();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public boolean isInLastPage() {
        if (mLastReceivedComics == null) return true;
        return mLastReceivedComics.isInLastPage();
    }

    protected void doSubscribe(Observable<Comics> comicsObservable) {

        comicsObservable
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        ((ComicsContract.ListView)mComicsView).setLoading(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.toString());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturnItem(new Comics())
                .subscribe(mComicsConsumer);
    }

    private ComicsContract.ListView getComicsView() {
        return (ComicsContract.ListView) mComicsView;
    }
}
