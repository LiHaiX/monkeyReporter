package com.mqunar.jonsnow.entity;

import java.io.Serializable;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class RemoteConfig implements Serializable{
    private static final long serialVersionUID = 1L;

    public String[] blackName;
    public MappingFile mapping;




    public static class MappingFile implements Serializable{
        private static final long serialVersionUID = 1L;

        public int atom_version;
        public String url;
        public boolean forceRefresh = false;
    }


}
