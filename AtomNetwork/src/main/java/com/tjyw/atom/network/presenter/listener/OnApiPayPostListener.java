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

    interface PostPayServiceListener {

        void postOnPayServiceSuccess(PayService payService);
    }

    interface PostPayOrderListener {

        void postOnPayOrderSuccess(PayOrder payOrder);
    }

    interface PostPayOrderListListener {

        void postOnPayOrderListSuccess(RetroListResult<Order> result);
    }

    interface PostPayOrderNameListListener {

        void postOnPayOrderNameListSuccess(PayOrder payOrder);
    }

    interface PostPayPreviewListener {

        void postOnPayPreviewSuccess(RetroPayPreviewResult result);
    }
}
