package com.mqunar.jonsnow.service.gitlab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mqunar.jonsnow.entity.Milestone;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/14.
 */
public class MilestoneService {

    private static final String URL_GET_ALL_MILESTONE = UrlUtils.GITLAB_HOST + "projects/" + UrlUtils.ADR_ATOM_FLIGHT_PROJECT_ID + "/milestones";
    private static final String MILESTON_TITLE_PREFIX = "online_crash-";

    public List<Milestone> fetchAllMilestones() {
        Network network = new Network();
        try {
            String contents = network.getFromUrlWithToken(URL_GET_ALL_MILESTONE);
            return JSON.parseObject(contents, new TypeReference<List<Milestone>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Milestone getMilestoneByVersion(int atomVersion) {
        String title = MILESTON_TITLE_PREFIX + atomVersion;
        List<Milestone> allMilestones = fetchAllMilestones();
        if (allMilestones != null) {
            for (Milestone milestone : allMilestones) {
                if (title.equals(milestone.title)) {
                    return milestone;
                }
            }
        }
        //if not found , create new milestone
        return createMilestone(title);
    }


    public Milestone createMilestone(String title) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", title);
        params.put("description", "auto created milestone to fix online crash");
        params.put("due_date", getLastDayOfMonth());
        Network network = new Network();
        try {
            String response = network.postFromUrlWithToken(URL_GET_ALL_MILESTONE, params);
            return JSON.parseObject(response, Milestone.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DATE));
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(calendar.getTime());
    }


}
