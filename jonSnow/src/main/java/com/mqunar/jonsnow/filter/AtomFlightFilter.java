package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.utils.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 如果stack trace中含有atom flight字样，则认为是有效的崩溃
 * 并且只比较有atom flight那几行
 * Created by ironman.li on 2016/7/13.
 */
public class AtomFlightFilter implements Filter {

    public static final String CLASS_PREFIX = "com.mqunar.atom.flight";
    private MessageDigest digest;


    public AtomFlightFilter() {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filter(List<CrashDetail> data) {
        Iterator<CrashDetail> iterator = data.iterator();
        CrashDetail item;
        try {
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item.stackTrace == null || item.stackTrace.length == 0) {
                    iterator.remove();
                } else {
                    StringBuilder sb = new StringBuilder();
                    ArrayList<String> keyLines = new ArrayList<>();
                    for (String line : item.stackTrace) {
                        line = line.replaceAll("\n", "");
                        line = line.replaceAll("\t", "");
                        if (line.contains(CLASS_PREFIX) && line.startsWith("at")) {
                            System.out.println(line);
                            keyLines.add(line);
                            line = line.replaceAll(" ", "");
                            sb.append(line);
                        }
                    }
                    String key = sb.toString();
                    if ("".equals(key)) {
                        iterator.remove();
                    } else {
                        item.key = key;
                        item.keyLines = keyLines;
                        byte[] bytes = digest.digest(key.getBytes("utf-8"));
                        BigInteger bigInt = new BigInteger(1, bytes);
                        String hashCode = bigInt.toString(16);
                        while (hashCode.length() < 32) {
                            hashCode = "0" + hashCode;
                        }
                        item.crashId = hashCode;
                    }
                    System.out.println("<<<<<<<<<<<<<<<<<<");
                    System.out.println(key);
                    System.out.println(item.crashId);
                    System.out.println(TextUtils.join("\n\t\t", item.stackTrace));
                    System.out.println(">>>>>>>>>>>>>>>>>>");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public Filter next() {
        return null;
    }
}
