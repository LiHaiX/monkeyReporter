package com.mqunar.jonsnow.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class UrlUtils {

    public static final String GITLAB_HOST = "http://gitlab.corp.qunar.com/api/v3/";

    public static final int MARK_ONE_PROJECT_ID = 20941;
    public static final int ADR_ATOM_FLIGHT_PROJECT_ID = 6201;

    private static final String URL_GET_CRASH_COUNT = "http://acra.corp.qunar.com/acra-android-flight/_design/acra-storage/_view/reports-per-day-with-atom?group=true&startkey=[";
    private static final String URL_GET_CRASH_LIST = "http://acra.corp.qunar.com/acra-android-flight/_design/acra-storage/_view/recent-items-by-atomver?descending=true&endkey=[";
    public static final String URL_GET_CRASH_DETAIL = "http://acra.corp.qunar.com/acra-android-flight/";


    public static String getCrashCountUrl(Calendar startKey, Calendar endKey) {
        return URL_GET_CRASH_COUNT + formatDate(startKey) + "]&endkey=[" + formatDate(endKey) + "]";
    }

    public static String getCrashCountUrl() {
        Calendar today = new GregorianCalendar();
        Calendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DATE, -1);
        return getCrashCountUrl(yesterday, today);
    }

    public static String formatDate(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        sb.append(year).append(",").append(month).append(",").append(day);
        return sb.toString();
    }

    public static String getCrashListUrl(String atom_version, int crashCount) {
        StringBuilder sb = new StringBuilder(URL_GET_CRASH_LIST);
        sb.append(atom_version).append("]&include_docs=false&limit=");
        sb.append(crashCount).append("&reduce=false&stale=update_after&startkey=[").append(atom_version);
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(",\"").append(sf.format(date)).append("T00:00:00.000Z\"]");
        System.out.println("crashlist : "+ sb.toString());
        return sb.toString();
    }


    public static String getFileContentUrl(String projectId, String branch, String path) throws UnsupportedEncodingException {
        return GITLAB_HOST + "projects/" + projectId + "/repository/files?ref=" + branch + "&file_path=" + URLEncoder.encode(path, "utf-8");
    }
}
