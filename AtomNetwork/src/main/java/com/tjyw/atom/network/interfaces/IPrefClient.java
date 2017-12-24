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

        String KEY_NEW_FLAG_COUPON = "KEY_NEW_FLAG_COUPON";

        String KEY_SHOW_APP_GUIDE_VER = "KEY_SHOW_APP_GUIDE_VER";
    }

    @Accessor(key = KEY.KEY_CLIENT_INIT)
    String getClientInit();

    @Accessor(key = KEY.KEY_CLIENT_INIT)
    void setClientInit(String clientInit);

    @Accessor(key = KEY.KEY_NEW_FLAG_COUPON)
    boolean getNewFlagCoupon();

    @Accessor(key = KEY.KEY_NEW_FLAG_COUPON)
    void setNewFlagCoupon(boolean newFlagCoupon);

    @Accessor(key = KEY.KEY_SHOW_APP_GUIDE_VER)
    int getShowAppGuideVersion();

    @Accessor(key = KEY.KEY_SHOW_APP_GUIDE_VER)
    void setShowAppGuideVersion(int version);
}
