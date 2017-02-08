package amqo.com.comics.injection;

import amqo.com.comics.injection.modules.ComicsDetailModule;
import amqo.com.comics.injection.scopes.PerFragment;
import amqo.com.comics.views.detail.fragment.ComicItemDetailFragment;
import dagger.Subcomponent;

@PerFragment
@Subcomponent( modules = { ComicsDetailModule.class })
public interface ComicDetailComponent extends BaseComicsComponent<ComicItemDetailFragment>{
}
