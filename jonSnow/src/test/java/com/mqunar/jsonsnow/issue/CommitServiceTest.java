package com.mqunar.jsonsnow.issue;

import com.mqunar.jonsnow.service.gitlab.CommitService;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ironman.li on 2016/7/28.
 */
public class CommitServiceTest {

    private CommitService commitService;


    @Before
    public void init() {
        Network network = new Network();
        commitService = new CommitService(network, String.valueOf(UrlUtils.ADR_ATOM_FLIGHT_PROJECT_ID));
    }


    @Test
    public void testGetOneCommit() {
//        commitService.getOneCommit("06a675d1a72a4ddf61943d0e7d1268666b9df5c1");
//        commitService.getOneCommit("d3ab098ee305a9dbfc4a81ecad07782153b77e36");
        commitService.getOneCommit("f87374a14a78c1df5dfa8d8f7f05deddb6ab7346");
    }


    @Test
    public void testGetCommitByPath() {
//        String path = "SRC/app/build.gradle";
        String path = ".gitignore";
        String path2 = "SRC/app/src/main/java/com/mqunar/atom/flight/activity/inland/FlightCityActivity.java";
        String s1 = commitService.getCommitByPath(path);
        String s2 = commitService.getCommitByPath(path2);
        Assert.assertEquals(s1,s2);
    }
}
