package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.utils.TextUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 根据从gitlab上动态获取的黑名单，剔除不需要提交的bug
 * Created by ironman.li on 2016/7/13.
 */
public class BlackNameFilter implements Filter {

    private String[] blackNames;

    public BlackNameFilter(String[] blackNames) {
        this.blackNames = blackNames;
    }

    @Override
    public void filter(List<CrashDetail> data) {
        if (blackNames != null && blackNames.length > 0) {
            Iterator<CrashDetail> iterator = data.iterator();
            CrashDetail item;
            while (iterator.hasNext()) {
                item = iterator.next();
                for (String blackName : blackNames) {
                    if (isContainKeywords(blackName, item)) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    private boolean isContainKeywords(String key, CrashDetail crashDetail) {
        if (TextUtils.isNotEmpty(crashDetail.signatureFull) && crashDetail.signatureDigest.contains(key)) {
            return true;
        } else if (TextUtils.isNotEmpty(crashDetail.signatureDigest) && crashDetail.signatureDigest.contains(key)) {
            return true;
        } else {
            if (crashDetail.stackTrace != null && crashDetail.stackTrace.length > 0) {
                for (String str : crashDetail.stackTrace) {
                    if (str.contains(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
