package com.tjyw.atom.network.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by stephen on 6/28/16.
 */
public class Documents {

    public static Documents getInstance(Context context) {
        Documents documents = new Documents(context);
        return documents;
    }

    public static Documents getInstance(Context context, String folder) {
        Documents documents = new Documents(context);
        documents.shift(folder);
        return documents;
    }

    static final String adrTodayDating = "adrTodayDating";

    static final String ROOT = "adr";

    static final long REQUIRED_BYTE = 10 * 1024 * 1024L;

    public final static String JPG = "%s.jpg";

    public final static String gallery = "gallery";

    public final static String issue = "issue";

    public final static String avatar = "avatar";

    public final static String cache = "cache";

    public final static String crash = "crash";

    protected File root;

    protected File handling;

    protected Documents(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            root = Environment.getExternalStorageDirectory();
            root = new File(root, adrTodayDating);
            if (! root.exists()) root.mkdirs();
            root = new File(root, ROOT);
            if (! root.exists()) root.mkdirs();

            handling = new File(root.getAbsolutePath());
        }
    }

    /** 工作目录是否存在 */
    public boolean available() {
        return null != handling && handling.exists();
    }

    /** 切换的工作目录 */
    public boolean shift(String folder) {
        if (available()) {
            handling = new File(root, folder);
            return handling.exists() || handling.mkdirs();
        } else {
            return false;
        }
    }

    /** 创建（而不是生成）一个新的文件 */
    public File newFile(String name) {
        if (available()) {
            return new File(handling, name);
        } else {
            return null;
        }
    }

    /** 创建（而不是生成）一个新的文件 */
    public File holdNewFile(String name) {
        if (available()) {
            return handling = new File(handling, name);
        } else {
            return null;
        }
    }

    public File getHandling() {
        return handling;
    }

    /** 判断剩余空间是否大于10MB */
    public static boolean availableStorage() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return (availableBlocks * blockSize) > REQUIRED_BYTE;
        } else {
            return false;
        }
    }
}
