package com.mqunar.jonsnow.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by ironman.li on 2016/7/8.
 */
public class FileUtils {

    public static final String CRASH_LIST_FILE_NAME = "crash_list.txt";
    public static final String MAPPING_FILE_NAME = "mapping.txt";


    public static File getHostFolder() {
        File file = null;
        try {
            file = new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return file.getParentFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getCacheFolder(String cacheName) {
        File file = new File(getHostFolder(), cacheName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getCacheFolder(){
        return getCacheFolder("flight-cache");
    }


    public static File getCrashListFile() {
        File folder = getHostFolder();
        return new File(folder, CRASH_LIST_FILE_NAME);
    }

    public static void write2File(String content, File file) {

    }





}
