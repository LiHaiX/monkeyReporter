package com.mqunar.jonsnow.service.gitlab;

import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.IOException;

/**
 * Created by ironman.li on 2016/7/28.
 */
public class RepositoryService {

    private Network network;
    private String projectId;

    public RepositoryService(Network network, String projectId) {
        this.network = network;
        this.projectId = projectId;
    }

    public void getAllContributors() {
        String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/repository/contributors";
        try {
            String result = network.getFromUrlWithToken(url);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
