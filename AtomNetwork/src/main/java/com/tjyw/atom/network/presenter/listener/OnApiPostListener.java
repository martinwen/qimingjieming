package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.PayOrder;

/**
 * Created by stephen on 17-8-18.
 */
public interface OnApiPostListener {

    interface PostPayOrderListener {

        void postOnPayOrderSuccess(PayOrder payOrder);
    }
}
