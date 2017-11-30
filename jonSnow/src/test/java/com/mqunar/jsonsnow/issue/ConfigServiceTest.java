package com.mqunar.jsonsnow.issue;

import com.alibaba.fastjson.JSON;
import com.mqunar.jonsnow.entity.RemoteConfig;
import com.mqunar.jonsnow.service.gitlab.ConfigService;
import com.mqunar.jonsnow.net.Network;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class ConfigServiceTest {

    private Network network;


    @Before
    public void init() {
        network = new Network();
    }

    @Test
    public void testFetchConfig() {
        ConfigService configService = new ConfigService(network);
        RemoteConfig config = configService.fetchRemoteConfig();
        System.out.println(JSON.toJSONString(config));

    }
}
