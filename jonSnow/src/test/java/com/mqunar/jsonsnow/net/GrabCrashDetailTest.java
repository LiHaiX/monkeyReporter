package com.mqunar.jsonsnow.net;

import com.mqunar.jonsnow.entity.CrashCount;
import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.CrashItem;
import com.mqunar.jonsnow.service.grab.GrabCrashCount;
import com.mqunar.jonsnow.service.grab.GrabCrashDetail;
import com.mqunar.jonsnow.service.grab.GrabCrashList;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/13.
 */
public class GrabCrashDetailTest {

    @Test
    public void grabDetailTest() {
        Network network = new Network();
        GrabCrashCount grabCrashCount = new GrabCrashCount(network);
        CrashCount crashCount = grabCrashCount.getLatestCrashCount(UrlUtils.getCrashCountUrl());
        GrabCrashList grabCrashList = new GrabCrashList(network);
        String content = grabCrashList.getCrashList(crashCount.getAtomVersion(), crashCount.getCrashCount());
        List<CrashItem> result = grabCrashList.deserializeCrashItemFromContent(content);
        GrabCrashDetail detailCrabber = new GrabCrashDetail(network, UrlUtils.URL_GET_CRASH_DETAIL);
        List<CrashDetail> res = detailCrabber.grabContent(result);
        for (CrashDetail detail : res) {
            System.out.print(Arrays.toString(detail.stackTrace));
        }


    }
}
