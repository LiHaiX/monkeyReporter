package com.mqunar.jonsnow.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ironman.li on 2016/7/8.
 */
public class CrashCount extends BaseEntity implements Comparable<CrashCount>{
    public String[] key;
    public int value;


    @Override
    public int compareTo(CrashCount o) {
        if (Integer.parseInt(o.key[4]) > Integer.parseInt(this.key[4])) {
            return -1;
        } else if (Integer.parseInt(o.key[4]) < Integer.parseInt(this.key[4])) {
            return 1;
        } else {
            return 0;
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public String getAtomVersion() {
        return  key[4];
    }

    @JSONField(serialize = false, deserialize = false)
    public int getCrashCount() {
        return value;
    }

    @Override
    public String toString() {
        return Arrays.toString(key) + " " + value;
    }
}
