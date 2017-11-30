package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.retrace.ReTrace;
import com.mqunar.jonsnow.utils.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class ReTraceFilter implements Filter {


    private String mappingFilePath;

    public ReTraceFilter(String mapUrl) {
        this.mappingFilePath = mapUrl;
    }

    @Override
    public void filter(List<CrashDetail> data) {
        String regex = "(?:\\s*%c:.*)|(?:\\s*at\\s+%c.%m\\s*\\(.*?(?::%l)?\\)\\s*)";
        File mappingFile = new File(mappingFilePath);
        if (!mappingFile.exists()) {
            return;
        }
        ReTrace reTrace = new ReTrace(regex, false, mappingFile);
        try{
            if (data != null && data.size() > 0) {
                reTrace.init();
                for (CrashDetail crashDetail : data) {
                    System.out.println("-----------before trace");
                    System.out.println(TextUtils.join("\n", crashDetail.stackTrace));
                    System.out.println("-----------");
                    crashDetail.stackTrace = reTrace.execute(crashDetail.stackTrace);
                    System.out.println("-----------after trace");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

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
