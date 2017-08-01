package com.tjyw.atom.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by stephen on 6/22/16.
 */
public class GmNet {
	
	public static int[] getNetworkType(Context context) {
		if (null != context) { 
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != connectivity) {
				NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
				if (null != networkInfo) {
					return new int[] { networkInfo.getType(), networkInfo.getSubtype() };
				}
			}
		}
		
		return null;
	}

	public static int getType(Context context) {
		int[] types = getNetworkType(context);
        return null == types ? -1 : types[0];
	}

	public static NetworkInfo getActiveNetworkInfo(Context context) {
		if (null != context) {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != connectivity) {
				return connectivity.getActiveNetworkInfo();
			} else {
                return null;
            }
		} else {
            return null;
        }
	}

	public static boolean checkNetworkType(Context context) {
		return null != getActiveNetworkInfo(context);
	}

    public static boolean isNetworkTypeWifi(Context context) {
        if (null == context) {
            return false;
        } else {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connectivity) {
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
                return null != networkInfo && ConnectivityManager.TYPE_WIFI == networkInfo.getType();
            } else {
                return false;
            }
        }
    }

}