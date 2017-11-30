package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class RemoveSameFilter implements Filter {


    @Override
    public void filter(List<CrashDetail> data) {
        HashMap<String, CrashDetail> map = new HashMap<String, CrashDetail>(data.size());
        for (CrashDetail crashDetail : data) {
            if (map.get(crashDetail.crashId) != null) {
                map.get(crashDetail.crashId).count++;
            } else {
                crashDetail.count = 1;
                map.put(crashDetail.crashId, crashDetail);
            }
        }
        data.clear();
        data.addAll(map.values());
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public Filter next() {
        return null;
    }
}
