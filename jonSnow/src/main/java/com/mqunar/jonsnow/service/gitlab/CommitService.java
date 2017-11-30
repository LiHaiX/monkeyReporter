package com.mqunar.jonsnow.service.gitlab;

import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.IOException;

/**
 * Created by ironman.li on 2016/7/28.
 */
public class CommitService {

    public static final String BRANCH = "release";

    private Network network;
    private String projectId;

    public CommitService(Network network, String projectId) {
        this.network = network;
        this.projectId = projectId;
    }

    public void getOneCommit(String commitId) {
        String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/repository/commits/" + commitId;
        try {
            String response = network.getFromUrlWithToken(url);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getCommitByPath(String path) {
        try {
            String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/repository/commits?path=" + path;
//            String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/repository/commits?path=\"" + path+"\"";
            System.out.println(url);
            String response = network.getFromUrlWithToken(url);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
