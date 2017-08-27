package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Pay;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
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

        String LIST = "pay/list";

        String ORDER = "pay/order";

        String PREVIEW = "pay/preview";
    }

    @FormUrlEncoded
    @POST(API.LIST)
    Observable<RetroResult<Pay>> postPay(
            @Field(IApiField.I.ignore) int ignore
    );

    @FormUrlEncoded
    @POST(API.ORDER)
    Observable<RetroResult<PayOrder>> postPayOrder(
            @Field(IApiField.V.vipId) int vipId,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber
    );

    @FormUrlEncoded
    @POST(API.PREVIEW)
    Observable<RetroResult<RetroPayPreviewResult>> postPayPreview(
            @Field(IApiField.V.vipId) int vipId,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber
    );
}
