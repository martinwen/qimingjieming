package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroOrderListResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;
import com.tjyw.atom.network.result.RetroResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 14/08/2017.
 */
public interface HttpPayServices {

    interface API {

        @Deprecated
        String LIST_VIP = "pay/listVip";

        String LIST_VIP_DISCOUNT = "pay/listVipDiscount";

        String PREVIEW = "pay/preview";

        String ORDER = "pay/wxOrder";

        String LOG = "pay/log";

        String ORDER_LIST = "order/list";

        String ORDER_NAME_LIST = "order/nameList";

        String ORDER_NAME_LIST_PACKAGE = "order/nameListPackage";

        String ORDER_UN_READ_NUM = "order/unReadNum";
    }

    interface VIP_ID {

        int RECOMMEND = 1;

        int TJJM = 2;

        int DJM = 3;

        int XJM = 4;

        int SUIT = 5;

        int NEW_SUIT = 6;
    }

    @FormUrlEncoded
    @POST(API.LIST_VIP_DISCOUNT)
    Observable<RetroResult<PayService>> postPayListVipDiscount(
            @Field(IApiField.T.type) int type,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.N.needSuit) int needSuit
    );

    @FormUrlEncoded
    @POST(API.LIST_VIP)
    Observable<RetroResult<PayService>> postPayListVip(
            @Field(IApiField.T.type) int type,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day
    );

    @FormUrlEncoded
    @POST(API.PREVIEW)
    Observable<RetroResult<RetroPayPreviewResult>> postPayPreview(
            @Field(IApiField.V.vipId) int vipId,
            @Field(IApiField.M.money) String money,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber,
            @Field(IApiField.R.redPacketId) Integer redPacketId
    );

    @FormUrlEncoded
    @POST(API.ORDER)
    Observable<RetroResult<PayOrder>> postPayOrder(
            @Field(IApiField.V.vipId) int vipId,
            @Field(IApiField.M.money) String money,
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber,
            @Field(IApiField.R.redPacketId) Integer redPacketId
    );

    @FormUrlEncoded
    @POST(API.LOG)
    Observable<RetroResult<REmptyResult>> postPayLog(
            @Field(IApiField.O.orderNo) String orderNo,
            @Field(IApiField.S.statusCode) String statusCode,
            @Field(IApiField.S.statusChannel) int statusChannel,
            @Field(IApiField.S.statusMsg) String statusMsg
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
            @Field(IApiField.T.type) Integer type,
            @Field(IApiField.O.offset) int offset,
            @Field(IApiField.L.limit) int limit
    );

    /**
     * 0::高分吉名 1::天降吉名 2::大吉名
     *
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(API.ORDER_NAME_LIST_PACKAGE)
    Observable<RetroResult<RetroListResult<List<NameDefinition>>>> postPayOrderNameListPackage(
            @Field(IApiField.O.orderNo) String orderNo
    );

    @FormUrlEncoded
    @POST(API.ORDER_UN_READ_NUM)
    Observable<RetroResult<PayOrderNumber>> postPayOrderUnReadNum(
            @Field(IApiField.I.ignore) int ignore
    );
}
