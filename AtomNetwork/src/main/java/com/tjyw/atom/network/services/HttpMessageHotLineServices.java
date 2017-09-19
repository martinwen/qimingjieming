package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.MessageConverse;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RUnreadResult;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 28/04/2017.
 */
public interface HttpMessageHotLineServices {

    interface API {

        String COUNT = "customerService/count";

        String DETAIL = "customerService/listDetail";

        String WRITE = "customerService/write";
    }

    @FormUrlEncoded
    @POST(API.COUNT)
    Observable<RetroResult<RUnreadResult>> postCustomerServiceCount(
            @Field(IApiField.I.id) int id
    );

    @FormUrlEncoded
    @POST(API.DETAIL)
    Observable<RetroResult<MessageConverse>> postCustomerServiceDetail(
            @Field(IApiField.I.id) int id
    );

    @FormUrlEncoded
    @POST(API.WRITE)
    Observable<RetroResult<REmptyResult>> postCustomerServiceWrite(
            @Field(IApiField.D.detail) String detail
    );
}
