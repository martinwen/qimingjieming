package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.model.PayOrderNumber;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.model.PayOrder;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.network.result.RetroPayPreviewResult;

import java.util.List;

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

    interface PostPayPackageListener {

        void postOnPayPackageSuccess(RetroListResult<List<NameDefinition>> result);
    }

    interface PostPayOrderUnReadNumListener {

        void postOnPayOrderUnReadNumSuccess(PayOrderNumber result);
    }
}
