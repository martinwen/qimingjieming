package com.tjyw.qmjm;

import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.drivemode.android.typeface.TypefaceHelper;
import com.tjyw.atom.pub.ForegroundCallbacks;

import java.lang.ref.WeakReference;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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

        registerActivityLifecycleCallbacks(ForegroundCallbacks.getInstance());

        ClientInitializer.getInstance()
                .atom(this, true)
                .fresco(this)
                .leakCanary(this)
                .crashReport(this)
                .faceBookStetho(this);

        TypefaceHelper.initialize(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/atom_pub_font_kati.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
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
