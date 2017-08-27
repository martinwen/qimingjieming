package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.Pay;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RetroPayPreviewResult;

/**
 * Created by stephen on 17-8-18.
 */
public interface OnApiPayPostListener {

    interface PostPayListener {

        void postOnPaySuccess(Pay pay);
    }

    interface PostPayOrderListener {

        void postOnPayOrderSuccess(PayOrder payOrder);
    }

    interface PostPayPreviewListener {

        void postOnPayPreviewSuccess(RetroPayPreviewResult result);
    }
}
