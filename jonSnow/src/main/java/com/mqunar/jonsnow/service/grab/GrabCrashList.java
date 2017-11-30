package com.mqunar.jonsnow.service.grab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.entity.CrashItem;
import com.mqunar.jonsnow.utils.FileUtils;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class GrabCrashList {

    private Network network;

    public GrabCrashList(Network network) {
        this.network = network;
    }

    public String getCrashList(String atom_version, int crashCount) {
        String url = UrlUtils.getCrashListUrl(atom_version, crashCount);
        String resultStr = null;
        try {
            resultStr = network.getFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStr;
    }


    public void writCrashList2File(String content) {
        File file = FileUtils.getCrashListFile();
        Writer fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 解析出主要字段
     * @param content
     * @return
     */
    public List<CrashItem> deserializeCrashItemFromContent(String content) {
        JSONObject crash = JSON.parseObject(content);
        JSONArray rows = crash.getJSONArray("rows");
        ArrayList<CrashItem> result = new ArrayList<CrashItem>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            JSONObject object = (JSONObject) rows.get(i);
            CrashItem crashItem = new CrashItem();
            crashItem.id = object.getString("id");
            JSONObject signature = object.getJSONObject("value").getJSONObject("signature");
            crashItem.signatureDigest = signature.getString("digest");
            crashItem.signatureFull = signature.getString("full");
            result.add(crashItem);
        }
        return result;
    }

    public void grabDateAndWrite2File(String atom_version, int crashCount) {
        String content = getCrashList(atom_version, crashCount);
        writCrashList2File(content);
    }



}
