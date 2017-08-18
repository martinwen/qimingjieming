package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 14/08/2017.
 */
public interface HttpPayServices {

    interface API {

        String ORDER = "pay/order";
    }

    @FormUrlEncoded
    @POST(API.ORDER)
    Observable<RetroResult<PayOrder>> postPayOrder(
            @Field(IApiField.V.vipId) int vipId,
            @Field(IApiField.S.sessionKey) String sessionKey
    );
}
