package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.result.REmptyResult;
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

        String GET_LOGIN_CODE = "user/getLoginCode";

        String LOGIN = "user/login";
    }

    @FormUrlEncoded
    @POST(API.REGISTER)
    Observable<RetroResult<UserInfo>> postUserRegister(
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
}
