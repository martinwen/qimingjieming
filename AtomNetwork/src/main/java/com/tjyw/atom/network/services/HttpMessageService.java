package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.MessageConverse;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 6/22/16.
 */
public interface HttpMessageService {

    interface API {

        String COUNT = "message/count";

        String DETAIL = "message/listDetail";

        String WRITE = "message/write";

        String DETAIL_COUNT = "activityCount/detailCount";
    }

    interface COUNT_TYPE {

        int MESSAGE_LIST = 102;

        int MESSAGE_DETAIL = 103;
    }
    
    @FormUrlEncoded
    @POST(API.COUNT)
    Observable<RetroResult<RetroListResult<String>>> postMessageCount(@Field(IApiField.I.id) int id);

    @FormUrlEncoded
    @POST(API.DETAIL)
    Observable<RetroResult<MessageConverse>> postMessageDetail(@Field(IApiField.U.userId) String userId);

    @FormUrlEncoded
    @POST(API.WRITE)
    Observable<RetroResult<REmptyResult>> postMessageWrite(
            @Field(IApiField.U.userId) String userId,
            @Field(IApiField.D.detail) String detail,
            @Field(IApiField.T.type) int type,
            @Field(IApiField.Q.qaAnswer) int qaAnswer,
            @Field(IApiField.R.red) int red
    );

    @FormUrlEncoded
    @POST(API.DETAIL_COUNT)
    Observable<RetroResult<REmptyResult>> postMessageDetailCount(
            @Field(IApiField.U.userId) String userId,
            @Field(IApiField.T.type) int type
    );
}
