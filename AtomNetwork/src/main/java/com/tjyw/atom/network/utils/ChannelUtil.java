package com.tjyw.atom.network.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.tjyw.atom.network.conf.IChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import timber.log.Timber;

/**
 * Created by paizxin on 6/22/16.
 */
public class ChannelUtil {

    public static String PID = "20000";
    public static HashMap<String, String> channelNameMap = new HashMap<String, String>();

    static {
        channelNameMap.put(IChannel.C1000.CID, IChannel.C1000.NAME); // 360手机助手
        channelNameMap.put(IChannel.C1001.CID, IChannel.C1001.NAME); // 腾讯应用宝
        channelNameMap.put(IChannel.C1002.CID, IChannel.C1002.NAME); // 联通沃商城
        channelNameMap.put(IChannel.C1003.CID, IChannel.C1003.NAME); // 百度应用中心
        channelNameMap.put(IChannel.C1004.CID, IChannel.C1004.NAME); // PP助手
        channelNameMap.put(IChannel.C1005.CID, IChannel.C1005.NAME); // 豌豆荚
        channelNameMap.put(IChannel.C1006.CID, IChannel.C1006.NAME); // 安智市场
        channelNameMap.put(IChannel.C1007.CID, IChannel.C1007.NAME); // 木蚂蚁
        channelNameMap.put(IChannel.C1008.CID, IChannel.C1008.NAME); // N多
        channelNameMap.put(IChannel.C1009.CID, IChannel.C1009.NAME); // 小米
        channelNameMap.put(IChannel.C1010.CID, IChannel.C1010.NAME); // OPPO
        channelNameMap.put(IChannel.C1011.CID, IChannel.C1011.NAME); // VIVO
        channelNameMap.put(IChannel.C1012.CID, IChannel.C1012.NAME); // 华为
        channelNameMap.put(IChannel.C1013.CID, IChannel.C1013.NAME); // 魅族
        channelNameMap.put(IChannel.C1014.CID, IChannel.C1014.NAME); // 联想
        channelNameMap.put(IChannel.C1015.CID, IChannel.C1015.NAME); // 乐视
        channelNameMap.put(IChannel.C1016.CID, IChannel.C1016.NAME); // 安卓市场
        channelNameMap.put(IChannel.C1017.CID, IChannel.C1017.NAME); // 91助手
        channelNameMap.put(IChannel.C1018.CID, IChannel.C1018.NAME); // 机锋
        channelNameMap.put(IChannel.C1019.CID, IChannel.C1019.NAME); // 应用汇
        channelNameMap.put(IChannel.C1020.CID, IChannel.C1020.NAME); // 优亿市场
        channelNameMap.put(IChannel.C1021.CID, IChannel.C1021.NAME); // 网易应用中心
        channelNameMap.put(IChannel.C1022.CID, IChannel.C1022.NAME); // 搜狗应用中心
        channelNameMap.put(IChannel.C1023.CID, IChannel.C1023.NAME); // 易用汇金立
        channelNameMap.put(IChannel.C1024.CID, IChannel.C1024.NAME); // 三星
        channelNameMap.put(IChannel.C1025.CID, IChannel.C1025.NAME); // 中国移动MM
        channelNameMap.put(IChannel.C1026.CID, IChannel.C1026.NAME); // 锤子
        channelNameMap.put(IChannel.C9999.CID, IChannel.C9999.NAME); // 官方渠道
    }

    public ChannelUtil() {
    }

    /**
     * 根据apk包中META-INF下的渠道号标注用空文件名获取渠道对象，包括cid&pid&cname
     */
    public static ChannelObject getChannel(Context context) {
        ChannelObject defaultChannel = new ChannelObject();
        defaultChannel.cid = IChannel.C1000.CID;
        defaultChannel.cname = IChannel.C1000.NAME;
        defaultChannel.pid = PID;
        if (channelNameMap == null || channelNameMap.size() == 0) {
            return defaultChannel;
        }
        String channelId = getChannelId(context);
        if (!TextUtils.isEmpty(channelId)) {
            String channelName = channelNameMap.get(channelId);
            if (TextUtils.isEmpty(channelName)) {
                return defaultChannel;
            }
            ChannelObject channelObject = new ChannelObject();
            channelObject.cid = channelId;
            channelObject.pid = PID;
            channelObject.cname = channelName;
            return channelObject;
        }
        return defaultChannel;
    }

    /**
     * 根据apk包中META-INF下的渠道号标注用空文件名获取渠道号,如C1000
     */
    private static String getChannelId(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;

        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/glchannel")) {
                    String[] split = entryName.split("/");
                    if (split.length >= 2) {
                        ret = split[1];
                    }
                    break;
                }
            }
        } catch (IOException e) {
            Timber.tag(ChannelUtil.class.getSimpleName()).e(e.toString());
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        if (split.length >= 2) {
            Timber.tag(ChannelUtil.class.getSimpleName()).e("get cid from Apk/META-INF/: " + ret);
            return ret.substring(split[0].length() + 1);
        } else {
            return "";
        }
    }

    /**
     * Modifies, adds or deletes file(s) from a existing zip file.
     *
     * @param zipFile                           the original zip file
     * @param newZipFile                        the destination zip file
     * @param filesToAddOrOverwrite             the names of the files to add or modify from the original file
     * @param filesToAddOrOverwriteInputStreams the input streams containing the content of the files
     *                                          to add or modify from the original file
     * @param filesToDelete                     the names of the files to delete from the original file
     * @throws IOException if the new file could not be written
     */
    public static void modifyZipFile(File zipFile,
                                     File newZipFile,
                                     String[] filesToAddOrOverwrite,
                                     InputStream[] filesToAddOrOverwriteInputStreams,
                                     String[] filesToDelete) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(newZipFile));
            // add existing ZIP entry to output stream
            ZipInputStream zin = null;
            try {
                zin = new ZipInputStream(new FileInputStream(zipFile));
                ZipEntry entry = null;
                while ((entry = zin.getNextEntry()) != null) {
                    String name = entry.getName();

                    // check if the file should be deleted
                    if (filesToDelete != null) {
                        boolean ignoreFile = false;
                        for (String fileToDelete : filesToDelete) {
                            if (name.equalsIgnoreCase(fileToDelete)) {
                                ignoreFile = true;
                                break;
                            }
                        }
                        if (ignoreFile) {
                            continue;
                        }
                    }

                    // check if the file should be kept as it is
                    boolean keepFileUnchanged = true;
                    if (filesToAddOrOverwrite != null) {
                        for (String fileToAddOrOverwrite : filesToAddOrOverwrite) {
                            if (name.equalsIgnoreCase(fileToAddOrOverwrite)) {
                                keepFileUnchanged = false;
                            }
                        }
                    }

                    if (keepFileUnchanged) {
                        // copy the file as it is
                        out.putNextEntry(new ZipEntry(name));
                        IOUtils.copy(zin, out);
                    }
                }
            } finally {
                if (zin != null) zin.close();
            }

            // add the modified or added files to the zip file
            if (filesToAddOrOverwrite != null) {
                for (int i = 0; i < filesToAddOrOverwrite.length; i++) {
                    String fileToAddOrOverwrite = filesToAddOrOverwrite[i];
                    InputStream in = null;
                    try {
                        in = filesToAddOrOverwriteInputStreams[i];
                        out.putNextEntry(new ZipEntry(fileToAddOrOverwrite));
                        IOUtils.copy(in, out);
                        out.closeEntry();
                    } finally {
                        if (null != in) in.close();
                    }
                }
            }

        } finally {
            if (out != null) out.close();
        }

    }

    public static class ChannelObject implements Serializable {
        public String cid;
        public String cname;
        public String pid;

        public String getFullChannel() {
            return String.format("%s ( %s )", this.cid, this.cname);
        }
    }
}
