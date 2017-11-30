package com.mqunar.jsonsnow.utils;

import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class UrlUtilsTest {

    @Test
    public void testGetCrashCountUrl() {
        String startkey = "http://acra.corp.qunar.com/acra-android-flight/_design/acra-storage/_view/reports-per-day-with-atom?group=true&startkey=[2016,6,6]&endkey=[2016,6,7]";
        String url = UrlUtils.getCrashCountUrl();
//        Assert.assertEquals(startkey, url);
        System.out.println(url);
    }

    @Test
    public void testFormater() {
        Calendar yesterday = new GregorianCalendar(2016,6,1);
        yesterday.add(Calendar.DATE, -1);
        Assert.assertEquals("2016,5,30", UrlUtils.formatDate(yesterday));


    }

    @Test
    public void testUrlCrashList() {
        String url = "http://acra.corp.qunar.com/acra-android-flight/_design/acra-storage/_view/recent-items-by-atomver?descending=true&endkey=[84]&include_docs=false&limit=13&reduce=false&stale=update_after&startkey=[84,\"2016-07-08T00:00:00.000Z\"]";
        String acuUrl = UrlUtils.getCrashListUrl("84", 13);
//        Assert.assertEquals(url, acuUrl);
        System.out.println(acuUrl);
    }
}
