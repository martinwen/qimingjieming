package com.tjyw.qmjm;

import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import atom.pub.ForegroundCallbacks;

import java.lang.ref.WeakReference;

/**
 * Created by stephen on 1/6/16.
 */
public class ClientQmjmApplication extends MultiDexApplication {

    protected static WeakReference<ClientQmjmApplication> instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = new WeakReference<ClientQmjmApplication>(this);

        registerActivityLifecycleCallbacks(ForegroundCallbacks.getInstance());

        ClientInitializer.getInstance()
                .atom(this, true)
                .fresco(this)
//                .leakCanary(this)
                .uMeng(this, Configure.UMeng.APP_KEY)
                .faceBookStetho(this)
                .calligraphy();
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
