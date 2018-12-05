package com.tjyw.qmjmqd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.tjyw.atom.alipay.PayConfigure;
import com.tjyw.atom.network.utils.DateTimeUtils;
import com.tjyw.atom.network.utils.Documents;
import com.tjyw.qmjmqd.activity.ClientWelcomeActivity;

import java.io.File;
import java.lang.ref.WeakReference;

import atom.pub.ClientCrashReport;
import atom.pub.ClientInitializer;

/**
 * Created by stephen on 1/6/16.
 */
public class ClientQmjmApplication extends MultiDexApplication {

    protected static WeakReference<ClientQmjmApplication> instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = new WeakReference<ClientQmjmApplication>(this);

        ClientInitializer.getInstance()
                .registerActivityLifecycleCallbacks(this)
                .atomPubNetwork(this, Configure.Network.SERVER, new FlavorsConfig.NetworkBuildConfig())
                .fresco(this)
                .leakCanary(this)
                .faceBookStetho(this)
                .crashReport(this, new ClientCrashReport.OnCrashReportListener() {
                    @Override
                    public void crashOnCaughtException(Context context, Thread thread, Throwable ex) {
                        Intent intent = new Intent(context, ClientWelcomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

                    @Override
                    public void crashOnShowingToast(Context context) {
                        Toast.makeText(context, R.string.atom_pub_resStringNetworkBroken, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public File crashOnDumpExceptionFile(Context context, String namespace) {
                        Documents documents = Documents.getInstance(context, Documents.crash);
                        if (documents.available()) {
                            return documents.newFile(
                                    String.format(
                                            namespace,
                                            DateTimeUtils.printCalendarByPattern(DateTimeUtils.getCalendar(System.currentTimeMillis()), DateTimeUtils.yyyy_MM_dd),
                                            System.currentTimeMillis()
                                    )
                            );
                        } else {
                            return null;
                        }
                    }
                });

        PayConfigure.getInstance() // 支付通道参数设置
                .setContext(this)
                .setAppId(Configure.ALI.APP_ID)
                .setPartner(Configure.ALI.PARTNER)
                .setSeller(Configure.ALI.SELLER)
                .setRsaPrivate(Configure.ALI.RSA_PRIVATE)
                .setRsaPublic(Configure.ALI.RSA_PUBLIC)
                .setNotifyUrl(Configure.ALI.NOTIFY_URL)
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
