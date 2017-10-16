package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 14/08/2017.
 */

public interface HttpQmServices {

    interface API {

        String QM = "qm";

        String QM_DATA = "qmData";

        String QM_DATA_NORMAL = "qmDataNormal";

        String JM = "jm";
    }

    @FormUrlEncoded
    @POST(API.QM_DATA)
    Observable<RetroResult<RNameDefinition>> postNameDefinitionData(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber
    );

    @FormUrlEncoded
    @POST(API.QM_DATA_NORMAL)
    Observable<RetroResult<RNameDefinition>> postNameDefinitionDataNormal(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber
    );

    @FormUrlEncoded
    @POST(API.QM)
    Observable<RetroResult<RNameDefinition>> postNameDefinition(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender,
            @Field(IApiField.N.nameNumber) int nameNumber
    );

    @FormUrlEncoded
    @POST(API.JM)
    Observable<RetroResult<Explain>> postExplain(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.N.name) String name,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender
    );

}
