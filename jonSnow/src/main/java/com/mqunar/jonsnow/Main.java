package com.mqunar.jonsnow;

import com.mqunar.jonsnow.controler.GitlabControler;
import com.mqunar.jonsnow.controler.GrabController;
import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.RemoteConfig;
import com.mqunar.jonsnow.filter.FilterManager;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.service.gitlab.ConfigService;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class Main {

    public static void main(String[] args) {

        ConfigService configService = new ConfigService(new Network());
        RemoteConfig config = configService.fetchRemoteConfig();

        GrabController grabController = new GrabController();
        List<CrashDetail> totalCrashDetail = grabController.grabCrashDetailOnline();

        FilterManager filterManager = new FilterManager(config, grabController.getAtomVer());
        filterManager.initFilter();
        List<CrashDetail> filteredCrash = filterManager.startFilter(totalCrashDetail);

        GitlabControler gitlabControler = new GitlabControler();
        if (filteredCrash != null && filteredCrash.size() > 0) {
            gitlabControler.createIssues(filteredCrash.get(0).atomVer, String.valueOf(UrlUtils.ADR_ATOM_FLIGHT_PROJECT_ID), filteredCrash);
        }
    }

}
