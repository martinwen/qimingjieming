package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.result.RPayPacketResult;

/**
 * Created by stephen on 17-8-24.
 */
public interface OnApiUserPostListener {

    interface PostUserRegisterListener {

        void postOnUserRegisterSuccess(UserInfo result);
    }

    interface PostUserLoginCodeListener {

        void postOnUserLoginCodeSuccess(String mobile);
    }

    interface PostUserLoginListener {

        void postOnUserLoginSuccess(UserInfo result);
    }

    interface PostUserGetNewRedPacketListener {

        void postOnUserGetNewRedPacketSuccess(String message);
    }

    interface PostUserListPacketListener {

        void postOnUserListPacketSuccess(RPayPacketResult result);
    }
}
