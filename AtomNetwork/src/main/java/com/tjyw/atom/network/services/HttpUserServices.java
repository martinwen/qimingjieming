package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RPayPacketResult;
import com.tjyw.atom.network.result.RUserRegister;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 14/08/2017.
 */
public interface HttpUserServices {

    interface API {

        String REGISTER = "user/register";

        String NEW_REGISTER = "user/registerAndInit";

        String GET_LOGIN_CODE = "user/getLoginCode";

        String LOGIN = "user/login";

        interface PACKET {

            String GET_NEW_RED_PACKET = "user/getNewRedPacket";

            String LIST_PACKET = "user/listPacket";

            String MY_PACKET = "user/myPacket";

            String MY_PACKET_DISCOUNT = "user/myPacketDiscount";
        }
    }

    @FormUrlEncoded
    @POST(API.REGISTER)
    Observable<RetroResult<UserInfo>> postUserRegister(
            @Field(IApiField.I.ignore) int ignore
    );

    @FormUrlEncoded
    @POST(API.NEW_REGISTER)
    Observable<RetroResult<RUserRegister>> postNewUserRegister(
            @Field(IApiField.I.ignore) int ignore
    );

    @FormUrlEncoded
    @POST(API.GET_LOGIN_CODE)
    Observable<RetroResult<REmptyResult>> postUserGetLoginCode(
            @Field(IApiField.M.mobile) String mobile
    );

    @FormUrlEncoded
    @POST(API.LOGIN)
    Observable<RetroResult<UserInfo>> postUserLogin(
            @Field(IApiField.M.mobile) String mobile,
            @Field(IApiField.C.code) String code
    );

    @FormUrlEncoded
    @POST(API.PACKET.GET_NEW_RED_PACKET)
    Observable<RetroResult<REmptyResult>> postUserGetNewRedPacket(
            @Field(IApiField.I.id) int id
    );

    @FormUrlEncoded
    @POST(API.PACKET.LIST_PACKET)
    Observable<RetroResult<RPayPacketResult>> postUserListPacket(
            @Field(IApiField.I.ignore) int ignore
    );

    @FormUrlEncoded
    @POST(API.PACKET.MY_PACKET)
    Observable<RetroResult<RPayPacketResult>> postUserMyPacket(
            @Field(IApiField.V.vipId) int vipId
    );

    @FormUrlEncoded
    @POST(API.PACKET.MY_PACKET_DISCOUNT)
    Observable<RetroResult<RPayPacketResult>> postUserMyPacketDiscount(
            @Field(IApiField.V.vipId) int vipId
    );
}
