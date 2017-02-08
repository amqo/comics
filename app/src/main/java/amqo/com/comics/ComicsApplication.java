package amqo.com.comics;

import android.app.Application;

import amqo.com.comics.injection.ApplicationComponent;
import amqo.com.comics.injection.BaseComicsComponent;
import amqo.com.comics.injection.DaggerApplicationComponent;
import amqo.com.comics.injection.modules.ApplicationModule;
import amqo.com.comics.injection.modules.ComicsDetailParentModule;
import amqo.com.comics.injection.modules.ComicsListModule;
import amqo.com.comics.views.BaseComicsView;
import amqo.com.comics.views.detail.activity.ComicItemDetailActivity;
import amqo.com.comics.views.list.activity.ComicsListActivity;

public class ComicsApplication extends Application {

    private static ComicsApplication INSTANCE;

    private ApplicationComponent mApplicationComponent;

    private BaseComicsComponent mActiveComicsComponent;

    public static ComicsApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public void createComicsComponent(BaseComicsView view) {

        if (view instanceof ComicsListActivity) {

            mActiveComicsComponent = mApplicationComponent.getComicsItemListComponent(
                    new ComicsListModule(view));

        } else if (view instanceof ComicItemDetailActivity) {

            mActiveComicsComponent = mApplicationComponent.getComicDetailComponent(
                    new ComicsDetailParentModule(view));
        }

    }

    public BaseComicsComponent getActiveComicComponent() {

        return mActiveComicsComponent;
    }
}
