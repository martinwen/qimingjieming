package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.Favorite;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RetroListResult;

/**
 * Created by stephen on 27/08/2017.
 */
public interface OnApiFavoritePostListener {

    interface PostListListener {

        void postOnFavoriteListSuccess(RetroListResult<Favorite> result);
    }

    interface PostAddListener {

        void postOnFavoriteAddSuccess(RIdentifyResult result, Object item);
    }

    interface PostRemoveListener {

        void postOnFavoriteRemoveSuccess(Object item);
    }
}
