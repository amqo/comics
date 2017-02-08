package amqo.com.comics.injection.modules;

import amqo.com.comics.injection.scopes.PerFragment;
import amqo.com.comics.model.view.ComicsContent;
import dagger.Module;
import dagger.Provides;

@Module
public class ComicsDetailModule {

    private final ComicsContent mComicsContent;

    public ComicsDetailModule(ComicsContent comicsContent) {
        mComicsContent = comicsContent;
    }

    @Provides @PerFragment
    ComicsContent providesComicsContent() {
        return mComicsContent;
    }

}
