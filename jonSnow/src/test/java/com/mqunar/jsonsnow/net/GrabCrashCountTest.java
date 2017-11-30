package com.mqunar.jsonsnow.net;

import com.alibaba.fastjson.JSON;
import com.mqunar.jonsnow.entity.CrashCount;
import com.mqunar.jonsnow.service.grab.GrabCrashCount;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Test;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class GrabCrashCountTest {

    @Test
    public void testGrabCount() {
        Network network =new Network();
        GrabCrashCount crashCountGraber = new GrabCrashCount(network);
        CrashCount crashCount =  crashCountGraber.getLatestCrashCount(UrlUtils.getCrashCountUrl());
        System.out.println(JSON.toJSONString(crashCount));

    }
}
