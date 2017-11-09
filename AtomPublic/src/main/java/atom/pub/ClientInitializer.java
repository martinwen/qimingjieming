package atom.pub;

import android.app.Application;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.NetworkFlavorsConfig;
import com.tjyw.atom.network.utils.Documents;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by stephen on 28/04/2017.
 */
public class ClientInitializer {

    private static volatile ClientInitializer _instance;

    private ClientInitializer() {

    }

    public static ClientInitializer getInstance() {
        if (_instance == null) {
            synchronized (ClientInitializer.class) {
                if (_instance == null) {
                    _instance = new ClientInitializer();
                }
            }
        }

        return _instance;
    }

    public ClientInitializer registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(ForegroundCallbacks.getInstance());
        return this;
    }

    /**
     * 初始化Atom的配置参数
     *
     * @param context
     * @param networkApiServer
     * @param networkFlavorsConfig
     * @return
     */
    public ClientInitializer atomPubNetwork(Context context, String networkApiServer, NetworkFlavorsConfig networkFlavorsConfig) {
        Network.getInstance() // 网络层服务器地址、渠道设置等
                .setNetworkApiServer(networkApiServer)
                .setNetworkFlavorsConfig(networkFlavorsConfig)
                .setContext(context)
                .setEnableStethoDebug(false);
        return this;
    }

    /**
     * 初始化Fresco图片加载工具
     *
     * @param context
     * @return
     */
    public ClientInitializer fresco(final Context context) {
        Fresco.initialize(context, ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context).setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        Documents documents = Documents.getInstance(context, Documents.cache);
                        return documents.getHandling();
                    }
                }).build()).build());

        return this;
    }

    /**
     * 初始化内存泄露工具
     *
     * @param context
     * @return
     */
    public ClientInitializer leakCanary(Application context) {
        if (! LeakCanary.isInAnalyzerProcess(context)) {
            LeakCanary.install(context);
        }

        return this;
    }

    /**
     * 初始化本地崩溃异常日志收集
     *
     * @param context
     * @return
     */
    public ClientInitializer crashReport(Context context, ClientCrashReport.OnCrashReportListener onCrashReportListener) {
        ClientCrashReport
                .getInstance()
                .setOnCrashReportListener(onCrashReportListener)
                .init(context);
        return this;
    }

    /**
     * 初始化友盟统计工具(Release版本可用)
     *
     * @param context
     * @param APP_KEY
     * @return
     */
    public ClientInitializer uMeng(Context context, String APP_KEY) {
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(
                context,
                APP_KEY,
                Network.getInstance().getFullChannel(),
                MobclickAgent.EScenarioType.E_UM_NORMAL
        );

        MobclickAgent.setDebugMode(false);
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.startWithConfigure(config);

        return this;
    }

    /**
     * 初始化Android开发调试工具(facebook)
     *
     * @param context
     * @return
     */
    public ClientInitializer faceBookStetho(Context context) {
        Stetho.initializeWithDefaults(context);
        return this;
    }

    /**
     * 初始化字体工具
     *
     * @return
     */
    public ClientInitializer calligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .disableCustomViewInflation()
                .disablePrivateFactoryInjection()
                .build()
        );
        return this;
    }

    public ClientInitializer clearUserLocalCacheData(Context context) {
        return this;
    }
}
