package com.mqunar.jonsnow.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class MappingEntity implements Serializable {

    public String fakeName;
    public String realName;

    public MappingEntity() {
        contents = new ArrayList<String>();
    }

    public List<String> contents;
}
