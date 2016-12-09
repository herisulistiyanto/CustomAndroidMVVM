package com.android.indie.school.cleancodemvvm.deps.module;

import com.android.indie.school.cleancodemvvm.BuildConfig;
import com.android.indie.school.cleancodemvvm.networking.NetworkService;
import com.android.indie.school.cleancodemvvm.networking.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by herisulistiyanto on 12/5/16.
 */
@Module
public class NetworkModule {
    File cacheFile;

    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("loggingInterceptor")
    public HttpLoggingInterceptor providesLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("baseOkhttp3")
    public OkHttpClient providesOkHttpClient3(@Named("loggingInterceptor") HttpLoggingInterceptor interceptor) {
        int timeout = 3;

        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("gson")
    public Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("baseRetrofit")
    public Retrofit provideBaseRetrofit(@Named("baseOkhttp3") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    @Named("baseNetworkService")
    public NetworkService providesNetworkService(@Named("baseRetrofit") Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(@Named("baseNetworkService") NetworkService networkService) {
        return new Service(networkService);
    }
}
