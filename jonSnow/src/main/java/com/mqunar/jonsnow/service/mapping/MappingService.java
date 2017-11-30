package com.mqunar.jonsnow.service.mapping;

import com.mqunar.jonsnow.entity.MappingEntity;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.service.BaseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ironman.li on 2016/7/29.
 */
public class MappingService extends BaseService {

    public MappingService(Network network) {
        super(network);
    }

    public Map<String, MappingEntity> fetchMappingContent(String url) throws IOException {
        String response = network.getFromUrl(url);
        return handleResult(response);
    }

    private Map<String, MappingEntity> handleResult(String response) {
        String[] results = response.split("\n");
        HashMap<String, MappingEntity> mappings = new HashMap<String, MappingEntity>();
        int index = 0;
        MappingEntity entity = null;
        while (index < results.length) {
            if (results[index].startsWith(" ") || results[index].startsWith("\t")) {
                entity.contents.add(results[index].trim());
            } else {
                if (entity != null) {
                    mappings.put(entity.fakeName, entity);
                }
                entity = new MappingEntity();
                String[] strs = results[index].split("->");
                entity.realName = strs[0].trim();
                entity.fakeName = strs[1].trim();
                if (entity.fakeName.endsWith(":")) {
                    entity.fakeName = entity.fakeName.substring(0, entity.fakeName.length() - 1);
                }
                System.out.println(entity.fakeName);
            }
            index++;
        }
        return mappings;
    }
}
