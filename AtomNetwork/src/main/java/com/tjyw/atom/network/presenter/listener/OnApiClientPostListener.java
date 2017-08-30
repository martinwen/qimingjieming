package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.ClientInit;

/**
 * Created by stephen on 30/08/2017.
 */
public interface OnApiClientPostListener {

    interface PostClientInitListener {

        void postOnClientInitSuccess(ClientInit clientInit);
    }

}
