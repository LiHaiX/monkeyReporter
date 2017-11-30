package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/13.
 */
public interface Filter {

    public void filter(List<CrashDetail> data);

    public boolean isOpen();

    public Filter next();
}
