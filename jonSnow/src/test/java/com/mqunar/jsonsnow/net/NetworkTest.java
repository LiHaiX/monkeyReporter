package com.mqunar.jsonsnow.net;

import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class NetworkTest {

    @Test
    public void testGraber() throws IOException {
        Network network = new Network();
        String result = network.getFromUrl(UrlUtils.getCrashCountUrl());
        System.out.println(result);
        Assert.assertNotNull(result);

    }
}
