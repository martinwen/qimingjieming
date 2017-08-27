package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 14/08/2017.
 */
public interface HttpClientServices {

    interface API {

        String INIT = "client/init";

        String ABOUT = "client/about";
    }

    @FormUrlEncoded
    @POST(API.INIT)
    Observable<RetroResult<ClientInit>> postClientInit(
            @Field(IApiField.I.ignore) int ignore
    );

    @FormUrlEncoded
    @POST(API.ABOUT)
    Observable<RetroResult<Explain>> postClientAbout(
            @Field(IApiField.I.ignore) int ignore
    );

}
