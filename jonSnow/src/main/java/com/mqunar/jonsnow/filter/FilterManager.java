package com.mqunar.jonsnow.filter;

import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.RemoteConfig;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/13.
 */
public class FilterManager {

    private ArrayList<Filter> filters = new ArrayList<Filter>();
    private RemoteConfig configs;
    private String atomVersion;

    public FilterManager(RemoteConfig configs, String atomVersion) {
        this.configs = configs;
        this.atomVersion = atomVersion;
    }

    public void initFilter() {
        BlackNameFilter blackNameFilter = new BlackNameFilter(configs.blackName);
        filters.add(blackNameFilter);
        filters.add(generateRetraceFilter());
        AtomFlightFilter atomFlightFilter = new AtomFlightFilter();
        filters.add(atomFlightFilter);
        RemoveSameFilter removeSameFilter = new RemoveSameFilter();
        filters.add(removeSameFilter);
    }

    public List<CrashDetail> startFilter(List<CrashDetail> source) {
        for (Filter filter : filters) {
            if (filter != null) {
                filter.filter(source);
            }
        }
        return source;
    }


    private ReTraceFilter generateRetraceFilter() {
        String fileName = downloadMapping(configs.mapping.url);
        if (fileName != null) {
            return new ReTraceFilter(fileName);
        }
        return null;
    }

    // TODO: 2016/8/1 you don't need to download mapping file every time
    private String downloadMapping(String url) {
        File file = new File(FileUtils.getCacheFolder(), FileUtils.MAPPING_FILE_NAME);
        // if mapping url is not right;
        if (String.valueOf(configs.mapping.atom_version).equals(atomVersion)) {
            return  null;
        }
        Network network = new Network();
        BufferedWriter writer = null;
        try {
            String content = network.getFromUrl(url);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
