package com.tjyw.atom.network.interfaces;

import android.content.Context;

import com.brianjmelton.stanley.annot.Accessor;
import com.brianjmelton.stanley.annot.Proxy;

/**
 * Created by stephen on 17-8-1.
 */
@Proxy(name = "IPrefClient", mode = Context.MODE_PRIVATE)
public interface IPrefClient {

    interface KEY {

        String KEY_CLIENT_INIT = "KEY_Client_INIT";
    }

    @Accessor(key = KEY.KEY_CLIENT_INIT)
    String getClientInit();

    @Accessor(key = KEY.KEY_CLIENT_INIT)
    void setClientInit(String clientInit);
}
