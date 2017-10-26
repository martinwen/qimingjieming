package atom.pub;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by stephen on 8/30/16.
 */
public class ClientCrashReport implements UncaughtExceptionHandler {

    static final String TAG = ClientCrashReport.class.getSimpleName();

    static final String CRASH_FILE = "crash.%s.%s.log";

    //系统默认的UncaughtException处理类   
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //CrashHandler实例  
    private static ClientCrashReport INSTANCE = new ClientCrashReport();

    //程序的Context对象  
    private Context mContext;

    //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();

    protected OnCrashReportListener onCrashReportListener;

    private ClientCrashReport() {

    }

    public static ClientCrashReport getInstance() {
        return INSTANCE;
    }

    public ClientCrashReport init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return this;
    }

    public ClientCrashReport setOnCrashReportListener(OnCrashReportListener onCrashReportListener) {
        this.onCrashReportListener = onCrashReportListener;
        return this;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (! handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Timber.tag(TAG).e(e, "Error!");
        }

        if (null != onCrashReportListener) {
            onCrashReportListener.crashOnCaughtException(mContext, thread, ex);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Timber.tag(TAG).e(e, "Error!");
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    protected boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {

            @Override
            public void run() {
                if (null != onCrashReportListener) {
                    Looper.prepare();
                    onCrashReportListener.crashOnShowingToast(mContext);
                    Looper.loop();
                }
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     * 
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Timber.tag(TAG).e("an error occured when collect package info");
        }
        infos.put("manufacture", Build.MANUFACTURER);
        infos.put("model", Build.MODEL);
        infos.put("device", Build.DEVICE);
        infos.put("os",Build.VERSION.RELEASE);
    }

    /**
     * 保存错误信息到文件中
     * 
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    protected void saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        Timber.tag(TAG).e(sb.toString());
        try {
            if (null != onCrashReportListener) {
                File crashFile = onCrashReportListener.crashOnDumpExceptionFile(mContext, CRASH_FILE);
                if (null != crashFile) {
                    FileOutputStream fos = new FileOutputStream(crashFile);
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            }
        } catch (Exception e) {
            Timber.tag(TAG).e("an error occured while writing file...", e);
        }
    }

    public interface OnCrashReportListener {

        void crashOnCaughtException(Context context, Thread thread, Throwable ex);

        void crashOnShowingToast(Context context);

        File crashOnDumpExceptionFile(Context context, String namespace);
    }
}
