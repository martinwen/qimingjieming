package com.tjyw.atom.network.interfaces;

import android.content.Context;

import com.brianjmelton.stanley.annot.Accessor;
import com.brianjmelton.stanley.annot.Proxy;

/**
 * Created by stephen on 17-8-1.
 */
@Proxy(name = "IPrefUser", mode = Context.MODE_PRIVATE)
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

    @Accessor(key = KEY.KEY_USER_SESSION, _String = "")
    String getUserSession();

    @Accessor(key = KEY.KEY_USER_SESSION)
    void setUserSession(String session);

    @Accessor(key = KEY.KEY_USER_INFO)
    String getUserInfo();

    @Accessor(key = KEY.KEY_USER_INFO)
    void setUserInfo(String userInfo);
}
