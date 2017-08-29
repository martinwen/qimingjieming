package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroListResult;
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

        String SHOW = "pay/show";

        String PREVIEW = "pay/preview";

        String ORDER = "pay/order";

        String ORDER_LIST = "order/list";

        String ORDER_NAME_LIST = "order/nameList";
    }

    @FormUrlEncoded
    @POST(API.SHOW)
    Observable<RetroResult<PayService>> postPayService(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day
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
    @POST(API.ORDER_LIST)
    Observable<RetroResult<RetroListResult<Order>>> postPayOrderList(
            @Field(IApiField.O.offset) int offset,
            @Field(IApiField.L.limit) int limit
    );

    @FormUrlEncoded
    @POST(API.ORDER_NAME_LIST)
    Observable<RetroResult<RNameDefinition>> postPayOrderNameList(
            @Field(IApiField.O.orderNo) String orderNo,
            @Field(IApiField.O.offset) int offset,
            @Field(IApiField.L.limit) int limit
    );
}
