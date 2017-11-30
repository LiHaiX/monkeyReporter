package com.mqunar.jsonsnow.service;

import com.mqunar.jonsnow.entity.MappingEntity;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.service.mapping.MappingService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class MappingServiceTest {

    private Network network;

    @Before
    public void init() {
        network = new Network();
    }

    @Test
    public void testFetchMapping() {
        try {
            MappingService service = new MappingService(network);
            String url = "http://cmyum.corp.qunar.com/mobile_app/android/m_adr_spider/spider_atom/m_adr_atom_flight/tags/r-160727-104745-billy.zhang/mapping.txt";
            Map<String, MappingEntity> result = service.fetchMappingContent(url);
//            System.out.println(JSON.toJSONString(result));
            System.out.println(result.keySet().size());
            System.out.println(result.get("com.mqunar.atom.flight.activity.maingui.x").realName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
