package com.tjyw.atom.network.presenter.listener;

import com.tjyw.atom.network.model.UserInfo;

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
}
