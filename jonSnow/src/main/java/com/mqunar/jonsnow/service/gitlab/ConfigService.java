package com.mqunar.jonsnow.service.gitlab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mqunar.jonsnow.entity.RemoteConfig;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.service.BaseService;
import com.mqunar.jonsnow.utils.UrlUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class ConfigService extends BaseService {

    public static final String CONFIG_FILE_PATH = "jonSnow/config.json";

    public ConfigService(Network network) {
        super(network);
    }

    public RemoteConfig fetchRemoteConfig() {
        try {
            String url = UrlUtils.getFileContentUrl(String.valueOf(UrlUtils.MARK_ONE_PROJECT_ID), "develop", CONFIG_FILE_PATH);
            String result = network.getFromUrlWithToken(url);
            JSONObject jsonObject = JSON.parseObject(result);
            String content = jsonObject.getString("content");
            String fileContent = new String(DatatypeConverter.parseBase64Binary(content), "utf-8");
            return JSON.parseObject(fileContent, RemoteConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
