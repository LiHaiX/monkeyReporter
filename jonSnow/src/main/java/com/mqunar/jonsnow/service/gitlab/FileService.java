package com.mqunar.jonsnow.service.gitlab;

import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ironman.li on 2016/7/28.
 */
public class FileService {

    public static final String BRANCH = "master";

    private Network network;
    private String projectId;


    public FileService(Network network, String projectId) {
        this.network = network;
        this.projectId = projectId;
    }

    public void getFileFromRepository(String filePath) {
        try {
            String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/repository/files?ref=" + BRANCH + "&file_path=" + URLEncoder.encode(filePath, "utf-8");
            System.out.println(url);
            String response = network.getFromUrlWithToken(url);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
