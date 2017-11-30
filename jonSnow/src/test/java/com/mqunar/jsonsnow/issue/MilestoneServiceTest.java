package com.mqunar.jsonsnow.issue;

import com.alibaba.fastjson.JSON;
import com.mqunar.jonsnow.entity.Milestone;
import com.mqunar.jonsnow.service.gitlab.MilestoneService;
import org.junit.Test;

import java.util.List;

/**
 * Created by ironman.li on 2016/7/14.
 */
public class MilestoneServiceTest {

    @Test
    public void testGetMilestone() {
        MilestoneService milestoneService = new MilestoneService();
        List<Milestone> result = milestoneService.fetchAllMilestones();
        System.out.println(JSON.toJSONString(result));
    }


    @Test
    public void testCreateMilestone() {
        MilestoneService milestoneService = new MilestoneService();
        Milestone milestone = milestoneService.createMilestone("mark_one_test");
        System.out.println(JSON.toJSONString(milestone));
    }

    @Test
    public void testGetLastDayOfMonth() {
//        MilestoneService manager = new MilestoneService();
//        String date = manager.getLastDayOfMonth();
//        System.out.println(date);
    }
}
