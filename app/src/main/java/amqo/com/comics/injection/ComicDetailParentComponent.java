package amqo.com.comics.injection;

import amqo.com.comics.injection.modules.ComicsDetailParentModule;
import amqo.com.comics.injection.modules.ComicsDetailModule;
import amqo.com.comics.injection.modules.NetworkModule;
import amqo.com.comics.injection.scopes.PerActivity;
import amqo.com.comics.views.detail.activity.ComicItemDetailActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent( modules = { NetworkModule.class, ComicsDetailParentModule.class })
public interface ComicDetailParentComponent extends BaseComicsComponent<ComicItemDetailActivity> {

    ComicDetailComponent getComicDetailComponent(
            ComicsDetailModule comicsDetailModule);
}
