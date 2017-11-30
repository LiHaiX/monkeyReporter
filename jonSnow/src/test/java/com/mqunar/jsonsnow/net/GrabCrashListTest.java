package com.mqunar.jsonsnow.net;

import com.mqunar.jonsnow.entity.CrashCount;
import com.mqunar.jonsnow.entity.CrashItem;
import com.mqunar.jonsnow.service.grab.GrabCrashCount;
import com.mqunar.jonsnow.service.grab.GrabCrashList;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/13.
 */
public class GrabCrashListTest {

    @Test
    public void grabCrashListTest() {
        Network network = new Network();
        GrabCrashCount grabCrashCount = new GrabCrashCount(network);
        CrashCount crashCount = grabCrashCount.getLatestCrashCount(UrlUtils.getCrashCountUrl());
        System.out.println(crashCount.toString());
        GrabCrashList grabCrashList = new GrabCrashList(network);
        grabCrashList.grabDateAndWrite2File(crashCount.getAtomVersion(), crashCount.getCrashCount());
    }

    @Test
    public void grabCrashAndDeserialize() {
        Network network = new Network();
        GrabCrashCount grabCrashCount = new GrabCrashCount(network);
        CrashCount crashCount = grabCrashCount.getLatestCrashCount(UrlUtils.getCrashCountUrl());
        System.out.println(crashCount.toString());
        GrabCrashList grabCrashList = new GrabCrashList(network);
        String content = grabCrashList.getCrashList(crashCount.getAtomVersion(), crashCount.getCrashCount());
        List<CrashItem> result = grabCrashList.deserializeCrashItemFromContent(content);
        for (CrashItem item : result) {
            System.out.println(item.id);
        }


    }
}
