package com.tjyw.atom.network.interfaces;

import com.brianjmelton.stanley.annot.Accessor;

/**
 * Created by stephen on 17-8-1.
 */
public interface IPrefUser {

    interface KEY {

        String KEY_USER_ID = "KEY_USER_ID";

        String KEY_USER_SESSION = "KEY_USER_SESSION";

        String KEY_USER_INFO = "KEY_USER_INFO";
    }

    @Accessor(key = KEY.KEY_USER_ID)
    String getUserId();

    @Accessor(key = KEY.KEY_USER_ID)
    void setUserId(String userId);

    @Accessor(key = KEY.KEY_USER_SESSION)
    String getUserSession();

    @Accessor(key = KEY.KEY_USER_SESSION)
    void setUserSession(String session);

    @Accessor(key = KEY.KEY_USER_INFO)
    String getUserInfo();

    @Accessor(key = KEY.KEY_USER_INFO)
    void setUserInfo(String userInfo);
}
