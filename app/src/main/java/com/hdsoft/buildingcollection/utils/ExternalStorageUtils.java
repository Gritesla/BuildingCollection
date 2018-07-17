package com.hdsoft.buildingcollection.utils;

import android.os.Environment;

import java.io.File;

public class ExternalStorageUtils {


    /**
     * @return bool
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean deleteFileByPath(String path) {
        boolean flag = false;
        File file = new File(path);
        if (file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    public static boolean dirIsExistOrCreateByPath(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                flag = true;
            } else {
                flag = file.mkdirs();
            }
        }
        return flag;

    }
}
