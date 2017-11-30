package com.mqunar.jonsnow.service;

import com.mqunar.jonsnow.net.Network;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class BaseService {

    protected Network network;
    public String projectId;

    public BaseService(Network network) {
        this.network = network;
    }

}
