package com.mqunar.jonsnow.service.grab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.CrashItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class GrabCrashDetail {

    private Network network;
    private String url;

    public GrabCrashDetail(Network network, String url) {
        this.network = network;
        this.url = url;
    }

    public List<CrashDetail> grabContent(List<CrashItem> crashItems) {
        List<CrashDetail> result = null;
        try {
            result = getCrashDetail(crashItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<CrashDetail> getCrashDetail(List<CrashItem> crashItems) throws IOException {
        ArrayList<CrashDetail> result = new ArrayList<CrashDetail>(crashItems.size());
        for (CrashItem item : crashItems) {
            String oneData = network.getFromUrl(url + item.id);
            CrashDetail detail = deserializeCrashDetail(oneData);
            detail.url = item.id;
            detail.signatureDigest = item.signatureDigest;
            detail.signatureFull = item.signatureFull;
            result.add(detail);
        }
        return result;
    }

    private CrashDetail deserializeCrashDetail(String content) {
        CrashDetail detail = new CrashDetail();
        JSONObject object = JSON.parseObject(content);
        detail.id = object.getString("_id");
        detail.appVersionName = object.getString("APP_VERSION_NAME");
        detail.brand = object.getString("BRAND");
        JSONObject atom = object.getJSONObject("CUSTOM_DATA").getJSONObject("atom");
        detail.atomName = atom.getString("name");
        detail.atomVer = atom.getIntValue("ver");
        detail.stackTrace = convertJsonArray2Array(object.get("STACK_TRACE"));
        detail.logcat = convertJsonArray2Array(object.get("LOGCAT"));
        detail.eventsLog = convertJsonArray2Array(object.get("EVENTSLOG"));
        return detail;
    }

    private String[] convertJsonArray2Array(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            String[] array = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                array[i] = (String) jsonArray.get(i);
            }
            return array;
        }
        return null;
    }


}
