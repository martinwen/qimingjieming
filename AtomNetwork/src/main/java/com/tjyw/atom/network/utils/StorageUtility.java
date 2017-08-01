package com.tjyw.atom.network.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by stephen on 7/9/16.
 */
public class StorageUtility {

	public static boolean chechExternalMemory(Context context){
		if (null != context) {
			if (! TextUtils.isEmpty(getExtStorageDirAbsPath())) {
				if (getAvailableExternalMemorySize() > 0) {
					return true;
				}
			}
		}
		
		return false;
	} 

	public static boolean checkExceedSdcardSize(long fileSize) {
		long s = getAvailableExternalMemorySize();
		return s <= 0 ? false : (fileSize >= s);
	}
	
	public static long getAvailableExternalMemorySize() {  
        long availableExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();  
            long availableBlocks = stat.getAvailableBlocks();  
            availableExternalMemorySize = availableBlocks * blockSize;  
        } else if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            availableExternalMemorySize = 0;  
        }
        
        return availableExternalMemorySize;  
    }  
	
	public static String getExtStorageDirAbsPath() {
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
	        File file = Environment.getExternalStorageDirectory();
	        if (null == file) {
	        	return null;
	        }
	        
	        if (file.exists()) { 
	        	return file.getAbsolutePath();
	        }
		}
		
		return null;
	}
	
	public static String byte2XB(long b) {
    	long i = 1L << 10L;
    	if(b < i) {
    		return b + "B";
    	}
    	
    	i = 1L << 20L;
    	if(b < i) {
    		return calXB(1F * b / (1L << 10L)) + "KB";
    	}
    	
    	i = 1L << 30L;
    	if(b < i) {
    		return calXB(1F * b / (1L << 20L)) + "MB";
    	}
    	
    	i = 1L << 40L;
    	if(b < i) {
    		return calXB(1F * b / (1L << 30L)) + "GB";
    	}
    	
    	return b + "B";
    }

	public static String calXB(float r) {
    	String result = r + "";
    	int index = result.indexOf(".");
		String s = result.substring(0, index + 1);
		String n = result.substring(index + 1);
		if (n.length() > 1) {
			n = n.substring(0, 1);
		}
		
		return s + n;
    }
}
