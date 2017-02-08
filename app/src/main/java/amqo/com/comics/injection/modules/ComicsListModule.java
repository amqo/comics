package amqo.com.comics.injection.modules;

import android.app.Activity;

import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.injection.scopes.PerActivity;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.contracts.ComicsAdapter;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicsContent;
import amqo.com.comics.views.BaseComicsView;
import amqo.com.comics.views.list.activity.ComicsRecyclerAdapter;
import amqo.com.comics.views.list.activity.ComicsListActivity;
import amqo.com.comics.views.list.activity.ComicsListPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ComicsListModule {

    private ComicsListActivity mComicItemListActivity;

    public ComicsListModule(BaseComicsView comicItemListActivity) {
        mComicItemListActivity = (ComicsListActivity) comicItemListActivity;
    }

    @Provides @PerActivity
    Activity providesActivity() {
        return mComicItemListActivity;
    }

    @Provides @PerActivity
    ComicsContract.View providesComicsItemListActivityView() {
        return mComicItemListActivity;
    }

    @Provides @PerActivity
    ComicsContract.Presenter providesComicsItemListActivityPresenter(
            ComicsEndpoint comicsEndpoint) {

        return new ComicsListPresenter(comicsEndpoint, mComicItemListActivity);
    }

    @Provides @PerActivity
    ComicsContent providesComicsContent() {
        return new ComicsContent(new Comics());
    }

    @Provides @PerActivity
    ComicsAdapter providesComicsAdapterView(
            ComicsContract.View view, ComicsContent comicsContent) {

        return new ComicsRecyclerAdapter(view, comicsContent);
    }
}
