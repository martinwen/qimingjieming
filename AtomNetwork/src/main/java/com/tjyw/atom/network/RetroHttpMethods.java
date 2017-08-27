package com.tjyw.atom.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjyw.atom.network.interceptor.RetroRequestInterceptor;
import com.tjyw.atom.network.services.HttpClientServices;
import com.tjyw.atom.network.services.HttpFavoriteService;
import com.tjyw.atom.network.services.HttpPayServices;
import com.tjyw.atom.network.services.HttpQmServices;
import com.tjyw.atom.network.services.HttpUserServices;

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

    protected HttpClientServices httpClientServices;

    protected HttpQmServices httpQmServices;

    protected HttpPayServices httpPayServices;

    protected HttpUserServices httpUserServices;

    protected HttpFavoriteService httpFavoriteService;

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
        httpClientServices = retrofit.create(HttpClientServices.class);
        httpQmServices = retrofit.create(HttpQmServices.class);
        httpPayServices = retrofit.create(HttpPayServices.class);
        httpUserServices = retrofit.create(HttpUserServices.class);
        httpFavoriteService = retrofit.create(HttpFavoriteService.class);
    }

    public static HttpClientServices CLIENT() {
        return getInstance().httpClientServices;
    }

    public static HttpQmServices NAMING() {
        return getInstance().httpQmServices;
    }

    public static HttpPayServices PAY() {
        return getInstance().httpPayServices;
    }

    public static HttpUserServices USER() {
        return getInstance().httpUserServices;
    }

    public static HttpFavoriteService FAVORITE() {
        return getInstance().httpFavoriteService;
    }
}
