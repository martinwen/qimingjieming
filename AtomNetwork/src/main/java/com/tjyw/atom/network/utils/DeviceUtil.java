package com.tjyw.atom.network.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tjyw.atom.network.Network;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by leonz on 15/6/29.
 */
public class DeviceUtil {

    static final String DEFAULT_DEVICE_ID = "000000000000";

    static String deviceID = null;

    public static String getDeviceID() {
        /* 未获取到有效的(非默认值)的deviceID,需要重新扫描 */
        if (TextUtils.isEmpty(deviceID) || DEFAULT_DEVICE_ID.equals(deviceID)) {
            try {
                String interfaceName = "wlan0";
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    if (intf.getName().equalsIgnoreCase(interfaceName)) {
                        byte[] mac = intf.getHardwareAddress();
                        if (mac != null) {
                            StringBuilder buf = new StringBuilder();
                            for (byte aMac : mac) {
                                buf.append(String.format("%02x", aMac));
                            }
                            deviceID = buf.toString();
                            return deviceID;
                        }
                    }
                }

                //特定机型or系统版本,遍历全部网卡接口仍未获取到"wlan0"网卡信息,填入默认uid
                deviceID = DEFAULT_DEVICE_ID;
            } catch (Throwable ex) {
                ex.printStackTrace();
                deviceID = DEFAULT_DEVICE_ID;
            }
            return deviceID;
        } else {
            return deviceID;
        }
    }

    public static int getClientVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static String getClientVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static String transformTelephonyManagerInfo() {
        try {
            TelephonyManager tm = (TelephonyManager) Network.getContext().getSystemService(Context.TELEPHONY_SERVICE);

            ObjectNode jObj = JsonUtil.getInstance().getEmptyNode();
            jObj.put("deviceId", tm.getDeviceId());
            jObj.put("deviceSoftwareVersion", tm.getDeviceSoftwareVersion());
            jObj.put("line1Number", tm.getLine1Number());
            jObj.put("networkCountryIso", tm.getNetworkCountryIso());
            jObj.put("networkOperator", tm.getNetworkOperator());
            jObj.put("networkOperatorName", tm.getNetworkOperatorName());
            jObj.put("phoneType", tm.getPhoneType());
            jObj.put("simCountryIso", tm.getSimCountryIso());
            jObj.put("simOperator", tm.getSimOperator());
            jObj.put("simOperatorName", tm.getSimOperatorName());
            jObj.put("simSerialNumber", tm.getSimSerialNumber());
            jObj.put("simState", tm.getSimState());
            jObj.put("subscriberId", tm.getSubscriberId());
            jObj.put("voiceMailNumber", tm.getVoiceMailNumber());

            GsmCellLocation gsmCellLocation = (GsmCellLocation) tm.getCellLocation();
            if (gsmCellLocation != null) {
                jObj.put("lac", gsmCellLocation.getLac());
                jObj.put("cellid", gsmCellLocation.getCid());
            }

            return JsonUtil.getInstance().toJsonString(jObj);
        } catch (Exception e) {
            return "";
        }
    }

    public static String transformBuildInfo() {
        try {
            ObjectNode version = JsonUtil.getInstance().getEmptyNode();
            version.put("INCREMENTAL", Build.VERSION.INCREMENTAL);
            version.put("RELEASE", Build.VERSION.RELEASE);
            version.put("CODENAME", Build.VERSION.CODENAME);
            version.put("SDK_INT", Build.VERSION.SDK_INT);

            ObjectNode jObj = JsonUtil.getInstance().getEmptyNode();
            jObj.set("VERSION", version);
            jObj.put("UNKNOWN", Build.UNKNOWN);
            jObj.put("ID", Build.ID);
            jObj.put("DISPLAY", Build.DISPLAY);
            jObj.put("PRODUCT", Build.PRODUCT);
            jObj.put("DEVICE", Build.DEVICE);
            jObj.put("BOARD", Build.BOARD);
            jObj.put("MANUFACTURER", Build.MANUFACTURER);
            jObj.put("BRAND", Build.BRAND);
            jObj.put("MODEL", Build.MODEL);
            jObj.put("BOOTLOADER", Build.BOOTLOADER);
            jObj.put("HARDWARE", Build.HARDWARE);
            jObj.put("SERIAL", Build.SERIAL);
            jObj.put("TYPE", Build.TYPE);
            jObj.put("TAGS", Build.TAGS);
            jObj.put("FINGERPRINT", Build.FINGERPRINT);
            jObj.put("RadioVersion", Build.getRadioVersion());
            jObj.put("TIME", Build.TIME);
            jObj.put("USER", Build.USER);
            jObj.put("HOST", Build.HOST);

            return JsonUtil.getInstance().toJsonString(jObj);
        } catch (Exception e) {
            return "";
        }
    }
}
