package amqo.com.comics.injection;

import javax.inject.Singleton;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.injection.modules.ApplicationModule;
import amqo.com.comics.injection.modules.ComicsDetailParentModule;
import amqo.com.comics.injection.modules.ComicsListModule;
import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    ComicsApplication application();

    ComicItemListComponent getComicsItemListComponent(
            ComicsListModule comicsListModule);

    ComicDetailParentComponent getComicDetailComponent(
            ComicsDetailParentModule comicsDetailParentModule);

}
