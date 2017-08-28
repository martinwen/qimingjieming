package com.tjyw.atom.network.services;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Favorite;
import com.tjyw.atom.network.result.REmptyResult;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stephen on 27/08/2017.
 */
public interface HttpFavoriteService {

    interface API {

        String ADD = "favorite/add";

        String REMOVE = "favorite/remove";

        String LIST = "favorite/list";
    }

    @FormUrlEncoded
    @POST(API.ADD)
    Observable<RetroResult<REmptyResult>> postFavoriteAdd(
            @Field(IApiField.S.surname) String surname,
            @Field(IApiField.N.name) String name,
            @Field(IApiField.D.day) String day,
            @Field(IApiField.G.gender) int gender
    );

    @FormUrlEncoded
    @POST(API.REMOVE)
    Observable<RetroResult<REmptyResult>> postFavoriteRemove(
            @Field(IApiField.I.id) int id
    );

    @FormUrlEncoded
    @POST(API.LIST)
    Observable<RetroResult<RetroListResult<Favorite>>> postFavoriteList(
            @Field(IApiField.I.ignore) int ignore
    );

}


