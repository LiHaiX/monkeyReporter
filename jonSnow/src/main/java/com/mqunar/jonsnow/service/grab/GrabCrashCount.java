package com.mqunar.jonsnow.service.grab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.entity.CrashCount;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/8.
 */
public class GrabCrashCount {

    private Network network;

    public GrabCrashCount(Network network) {
        this.network = network;
    }

    public CrashCount getLatestCrashCount(String url) {
        String crashStr = null;
        try {
            crashStr = network.getFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (crashStr != null) {
            JSONObject jsonObject = JSON.parseObject(crashStr);
            List<CrashCount> crashes = JSON.parseArray(jsonObject.get("rows").toString(), CrashCount.class);
            Iterator<CrashCount> iterator = crashes.iterator();
            while (iterator.hasNext()) {
                CrashCount crashCount = iterator.next();
                if ("null".equals(crashCount.key[3])) {
                    iterator.remove();
                }
            }
            Collections.sort(crashes);
            Collections.reverse(crashes);
            System.out.println(Arrays.toString(crashes.toArray()));
            return crashes.get(0);
        }
        return null;
    }

}
