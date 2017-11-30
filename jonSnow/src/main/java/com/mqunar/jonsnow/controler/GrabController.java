package com.mqunar.jonsnow.controler;

import com.mqunar.jonsnow.entity.CrashCount;
import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.CrashItem;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.service.grab.GrabCrashCount;
import com.mqunar.jonsnow.service.grab.GrabCrashDetail;
import com.mqunar.jonsnow.service.grab.GrabCrashList;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class GrabController {

    private String atomVersion;

    public List<CrashDetail> grabCrashDetailOnline() {
        Network network = new Network();
        GrabCrashCount grabCrashCount = new GrabCrashCount(network);
        CrashCount crashCount = grabCrashCount.getLatestCrashCount(UrlUtils.getCrashCountUrl());
        atomVersion = crashCount.getAtomVersion();
        GrabCrashList grabCrashList = new GrabCrashList(network);
        String content = grabCrashList.getCrashList(crashCount.getAtomVersion(), crashCount.getCrashCount());
        List<CrashItem> result = grabCrashList.deserializeCrashItemFromContent(content);
        GrabCrashDetail detailCrabber = new GrabCrashDetail(network, UrlUtils.URL_GET_CRASH_DETAIL);
        return detailCrabber.grabContent(result);
    }

    public String getAtomVer() {
        return atomVersion;
    }


    public List<CrashDetail> grabCrashDetailBeta() {
        return null;
    }
}
