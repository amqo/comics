package amqo.com.comics.injection.modules;

import android.app.Activity;

import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.injection.scopes.PerActivity;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicsContent;
import amqo.com.comics.views.BaseComicsView;
import amqo.com.comics.views.detail.activity.ComicDetailPresenter;
import amqo.com.comics.views.detail.activity.ComicItemDetailActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ComicsDetailParentModule {

    private ComicItemDetailActivity mComicItemDetailActivity;

    public ComicsDetailParentModule(BaseComicsView baseComicsView) {
        mComicItemDetailActivity = (ComicItemDetailActivity) baseComicsView;
    }

    @Provides @PerActivity
    Activity providesActivity() {
        return mComicItemDetailActivity;
    }

    @Provides @PerActivity
    ComicsContract.Presenter providesComicsDetailActivityPresenter(
            ComicsEndpoint comicsEndpoint) {

        return new ComicDetailPresenter(comicsEndpoint, mComicItemDetailActivity);
    }

    @Provides @PerActivity
    ComicsContract.View providesComicsDetailActivityView() {
        return mComicItemDetailActivity;
    }

    @Provides @PerActivity
    ComicsContent providesComicsContent() {
        return new ComicsContent(new Comics());
    }
}
