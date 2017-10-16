package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;

/**
 * Created by stephen on 17-8-18.
 */
public interface OnApiPayPostListener {

    interface PostPayListVipListener {

        void postOnPayListVipSuccess(int type, PayService payService);
    }

    interface PostPayOrderListener {

        void postOnPayOrderSuccess(PayOrder payOrder);
    }

    interface PostPayOrderListListener {

        void postOnPayOrderListSuccess(RetroListResult<Order> result);
    }

    interface PostPayPreviewListener {

        void postOnPayPreviewSuccess(RetroPayPreviewResult result);
    }
}
