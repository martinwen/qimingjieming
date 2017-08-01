package com.tjyw.atom.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjyw.atom.network.interceptor.RetroRequestInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

/**
 * Created by stephen on 6/22/16.
 */
public class RetroHttpMethods {

    static final String TAG = RetroHttpMethods.class.getName();

    static final int DEFAULT_TIMEOUT = 30;

    static final class SingletonHolder {

        static final RetroHttpMethods instance = new RetroHttpMethods();
    }

    public static RetroHttpMethods getInstance() {
        return SingletonHolder.instance;
    }

    protected Retrofit retrofit;

    public RetroHttpMethods() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Network.getInstance().isEnableStethoDebug()) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        retrofit = new Retrofit.Builder()
                .client(builder
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                Timber.tag(TAG).d(message);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor(new RetroRequestInterceptor())
                        .build()
                )
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Network.getInstance().getNetworkApiServer())
                .build();

        createApiService(retrofit);
    }

    protected void createApiService(Retrofit retrofit) {

    }
}
