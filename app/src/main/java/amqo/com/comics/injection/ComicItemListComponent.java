package amqo.com.comics.injection;

import amqo.com.comics.injection.modules.ComicsListModule;
import amqo.com.comics.injection.modules.NetworkModule;
import amqo.com.comics.injection.scopes.PerActivity;
import amqo.com.comics.views.list.activity.ComicsListActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent( modules = {NetworkModule.class, ComicsListModule.class })
public interface ComicItemListComponent extends BaseComicsComponent<ComicsListActivity>{
}
