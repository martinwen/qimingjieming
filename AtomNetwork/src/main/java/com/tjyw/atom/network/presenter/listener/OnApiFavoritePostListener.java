package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.Favorite;

import java.util.List;

/**
 * Created by stephen on 27/08/2017.
 */
public interface OnApiFavoritePostListener {

    interface PostFavoriteListListener {

        void postOnFavoriteListSuccess(List<Favorite> result);
    }

    interface PostFavoriteAddListener {

        void postOnFavoriteAddSuccess();
    }

    interface PostFavoriteRemoveListener {

        void postOnFavoriteRemoveSuccess();
    }
}
