package amqo.com.comics.injection.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Date;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.PrivateConstants;
import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.api.HashmapGenerator;
import amqo.com.comics.injection.scopes.PerActivity;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides @PerActivity
    Cache providesOkHttpCache(ComicsApplication application) {

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides @PerActivity
    HashmapGenerator providesHashmapGenerator() {

        return new HashmapGenerator();
    }

    @Provides @PerActivity
    Interceptor providesInterceptor(
            final HashmapGenerator hashmapGenerator) {

        Interceptor interceptor = new Interceptor() {

            final String KEY_PARAM = "apikey";
            final String TIMESTAMP_PARAM = "ts";
            final String HASH_PARAM = "hash";

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl.Builder urlBuilder = request.url().newBuilder();

                String timestamp = Long.toString(new Date().getTime());

                urlBuilder = urlBuilder
                        .addQueryParameter(TIMESTAMP_PARAM, timestamp)
                        .addEncodedQueryParameter(KEY_PARAM, PrivateConstants.MARVEL_PUBLIC_KEY)
                        .addQueryParameter(HASH_PARAM, hashmapGenerator.getHashForTimestamp(timestamp));

                HttpUrl url = urlBuilder.build();

                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };
        return interceptor;
    }

    @Provides @PerActivity
    OkHttpClient providesOkHttpClient(Cache cache, Interceptor interceptor) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.cache(cache);
        clientBuilder.interceptors().add(interceptor);
        OkHttpClient client = clientBuilder.build();
        return client;
    }

    @Provides @PerActivity
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ComicsEndpoint.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Provides @PerActivity
    ComicsEndpoint providesComicsEndpoint(Retrofit retrofit) {

        return retrofit.create(ComicsEndpoint.class);
    }


}
