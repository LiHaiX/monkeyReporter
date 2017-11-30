package com.mqunar.jonsnow.entity;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/13.
 */
public class CrashDetail extends BaseEntity {

    public String key;
    public int count;
    public String crashId;
    public String url;
    public List<String> keyLines;


    public String id;
    public String appVersionName;
    public String brand;
    public String atomName;
    public String signatureFull;
    public String signatureDigest;
    public int atomVer;
    public String[] eventsLog;
    public String[] logcat;
    public String[] stackTrace;


}
