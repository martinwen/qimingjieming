package com.tjyw.atom.network;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import timber.log.Timber;

/**
 * Created by stephen on 10/02/2017.
 */
public class Network {

    public static final String TAG = Network.class.getSimpleName();

    private static volatile Network _instance;

    private Network() {
        Timber.plant(new Timber.DebugTree());
    }

    public static Network getInstance() {
        if (_instance == null) {
            synchronized (Network.class) {
                if (_instance == null) {
                    _instance = new Network();
                }
            }
        }

        return _instance;
    }

    public static Context getContext() {
        return getInstance().context;
    }

    public static String pGetString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    protected int screenWidth = 480;

    protected int screenHeight = 800;

    protected Context context;

    protected String networkApiServer;

    protected String cid;

    protected String cname;

    protected String pid;

    protected boolean enableStethoDebug;

    public Network setContext(Context context) {
        this.context = context;

        if (null != context) {
            WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (null != mWindowManager) {
                DisplayMetrics metric = new DisplayMetrics();
                mWindowManager.getDefaultDisplay().getMetrics(metric);
                screenWidth = metric.widthPixels;
                screenHeight = metric.heightPixels;
            }

            initDatabases();
        }

        return this;
    }

    public String getNetworkApiServer() {
        return this.networkApiServer;
    }

    public Network setNetworkApiServer(String networkApiServer) {
        this.networkApiServer = networkApiServer;
        return this;
    }

    public String getCid() {
        return this.cid;
    }

    public Network setCid(String cid) {
        this.cid = cid;
        return this;
    }

    public String getPid() {
        return this.pid;
    }

    public Network setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getCname() {
        return cname;
    }

    public Network setCname(String cname) {
        this.cname = cname;
        return this;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public String getFullChannel() {
        return String.format("%s ( %s )", this.cid, this.cname);
    }

    public boolean isEnableStethoDebug() {
        return enableStethoDebug;
    }

    public void setEnableStethoDebug(boolean enableStethoDebug) {
        this.enableStethoDebug = enableStethoDebug;
    }

    protected static void initDatabases() {

    }
}
