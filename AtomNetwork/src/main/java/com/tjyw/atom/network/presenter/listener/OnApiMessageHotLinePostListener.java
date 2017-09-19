package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.MessageConverse;
import com.tjyw.atom.network.result.REmptyResult;

/**
 * Created by stephen on 19/09/2017.
 */
public interface OnApiMessageHotLinePostListener {

    interface PostMessageHotLineDetailListener {

        void postOnMessageHotLineDetailSuccess(MessageConverse result);
    }

    interface PostMessageHotLineWriteListener {

        void postMessageHotLineWriteSuccess(REmptyResult result);
    }
}
