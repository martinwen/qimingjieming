package com.tjyw.qmjmqd;

import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.tjyw.atom.alipay.PayConfigure;

import java.lang.ref.WeakReference;

import atom.pub.ClientInitializer;

/**
 * Created by stephen on 1/6/16.
 */
public class ClientQmjmApplication extends MultiDexApplication {

    public static int screenWidth = 480;

    public static int screenHeight = 800;

    protected static WeakReference<ClientQmjmApplication> instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = new WeakReference<ClientQmjmApplication>(this);

        ClientInitializer.getInstance()
                .registerActivityLifecycleCallbacks(this)
                .atomPubNetwork(this, Configure.Network.SERVER, new FlavorsConfig.NetworkBuildConfig())
                .fresco(this)
                .uMeng(this, Configure.UMeng.APP_KEY)
                .calligraphy();

        PayConfigure.getInstance() // 支付通道参数设置
                .setContext(this)
                .setAppId(Configure.ALI.APP_ID)
                .setPartner(Configure.ALI.PARTNER)
                .setSeller(Configure.ALI.SELLER)
                .setRsaPrivate(Configure.ALI.RSA_PRIVATE)
                .setRsaPublic(Configure.ALI.RSA_PUBLIC)
                .setNotifyUrl(Configure.ALI.NOTIFY_URL)
                .setWxAppId(Configure.WX.APP_ID) // 微信只需要APP_ID
                .dump();
    }

    public static ClientQmjmApplication getContext() {
        if (null == instance) {
            return null;
        }
        return instance.get();
    }

    public static String pGetString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    public static Resources pGetResources() {
        return getContext().getResources();
    }
}
