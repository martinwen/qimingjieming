package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.result.RNameDefinition;

/**
 * Created by stephen on 27/08/2017.
 */
public interface OnApiFavoritePostListener {

    interface PostFavoriteListListener {

        void postOnFavoriteListSuccess(RNameDefinition result);
    }

    interface PostFavoriteAddListener {

        void postOnFavoriteAddSuccess();
    }

    interface PostFavoriteRemoveListener {

        void postOnFavoriteRemoveSuccess();
    }
}
