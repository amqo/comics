package amqo.com.comics.injection.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import amqo.com.comics.ComicsApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    ComicsApplication mApplication;

    public ApplicationModule(ComicsApplication application) {
        mApplication = application;
    }

    @Provides @Singleton
    ComicsApplication providesApplication() {
        return mApplication;
    }

    @Provides @Singleton
    SharedPreferences provicesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }
}
