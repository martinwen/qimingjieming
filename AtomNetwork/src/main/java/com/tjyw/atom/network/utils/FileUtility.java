package com.tjyw.atom.network.utils;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by stephen on 7/9/16.
 */
public class FileUtility {
	
	private static final long ONE_DAY_IN_MILLIS = 1l* 1000 * 60 * 60 * 24;
	
	public static boolean outOfDate(File f, int days) {
		if (null == f) {
			return true;
		}
		
		try {
			long l = f.lastModified();
			long extra = l + ONE_DAY_IN_MILLIS * days;
			
			return System.currentTimeMillis() > extra;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			f = null;
		}
		
		return true;
	}

	public static boolean outOfDate(String fileName, int days) {
		if (TextUtils.isEmpty(fileName)) {
			return true;
		}
		
		return outOfDate(new File(fileName), days);
	}
	
	public static boolean exists(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return false;
		}
		
		File f = null;
		try {
			f = new File(fileName);
			return f.exists();
		} catch (Exception e) {
			
		} finally {
			f = null;
			fileName = null;
		}
		
		return false;
	}
	
	public static byte[] read2ByteArr(String fileName) {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		
		byte[] buffer = null;
		int len = 0;
		
		try {
			fis = new FileInputStream(fileName);
			baos = new ByteArrayOutputStream();
			
			buffer = new byte[fis.available()];
			
			while((len = fis.read(buffer)) != -1){
				baos.write(buffer, 0, len);
			}
			
			if (null != baos) {
				return baos.toByteArray();
			}
		} catch (Exception e) {

		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (Exception e) { }
			
			try {
				if (null != baos) {
					baos.close();
				}
			} catch (Exception e) { }
			
			fis = null;
			baos = null;
			buffer = null;
		}
		
		return null;
	}
    
    public static InputStream read2Stream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileName = null;
        }
        
        return null;
    }
	
	public static String read(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return null;
		}
		
		byte[] buffer = null;
		try {
		    buffer = read2ByteArr(fileName);
		    if (! ArrayUtil.isEmpty(buffer)) {
		        return new String(buffer);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileName = null;
			buffer = null;
		}
		
		return null;
	}

	public static boolean rmdir(String dir) {
	    if(dir == null){
	        return true;
	    }
		File f = new File(dir);
		if (!f.exists()) {
			return true;
		}
		
		if (f.isDirectory()) {
			for (String sub : f.list()) {
				rmdir(dir + File.separator + sub);
			}
		}

		return f.delete();
	}
	
	public static boolean mkdir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return false;
        }
        
        File file = null;
        try {
            file = new File(dir);
            if (! file.exists()) {
                file.mkdirs();
            }
            
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file = null;
            dir = null;
        }
        
        return false;
    }
	
	public static boolean writeFile(String fileAbsPath, byte[] buffer, boolean append) {
        FileOutputStream fos = null;
        
        try {
        	if (! ArrayUtil.isEmpty(buffer)) {
	            fos = new FileOutputStream(fileAbsPath, append);
	            fos.write(buffer);
	            
	            return true;
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) { }

                fos = null;
            }

            fileAbsPath = null;
            buffer = null;
        }
        
        return false;
    }

    public static long dirSize(File dir) {
        long result = 0;
        File[] fileList = dir.listFiles();
        if (null == fileList) return 0;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                result += dirSize(fileList[i]);
            } else {
                result += fileList[i].length();
            }
        }
        return result;
    }

    public static String fileSize(String path){
        File dir = new File(path);
        if (dir.exists()) {
            long result = dir.isFile() ? dir.length() : dirSize(dir);
            if (result < 10240) {
                return StorageUtility.byte2XB(0);
            } else {
                return StorageUtility.byte2XB(result);
            }
        } else {
            return StorageUtility.byte2XB(0);
        }
    }
}
