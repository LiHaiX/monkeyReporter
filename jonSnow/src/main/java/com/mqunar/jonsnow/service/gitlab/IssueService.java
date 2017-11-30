package com.mqunar.jonsnow.service.gitlab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mqunar.jonsnow.entity.Issue;
import com.mqunar.jonsnow.entity.Milestone;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.TextUtils;
import com.mqunar.jonsnow.utils.UrlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class IssueService {

    private Network network = new Network();

    public List<Issue> getAllIssueByProjectId(String id) {
        String url = UrlUtils.GITLAB_HOST + "projects/" + id + "/issues";
        try {
            String result = network.getFromUrlWithToken(url);
            return JSON.parseObject(result, new TypeReference<List<Issue>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNewIssues(String projectId, List<Issue> issues) {
        if (issues != null && issues.size() > 0) {
            try {
                for (Issue issue : issues) {
                    createNewIssue(projectId, issue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Issue createNewIssue(String projectId, Issue issue) throws IOException {
        String url = UrlUtils.GITLAB_HOST + "projects/" + projectId + "/issues";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", issue.title);
        params.put("description", issue.description == null ? "" : issue.description);
        if (issue.assignee != null && issue.assignee.id > 0) {
            params.put("assignee_id", String.valueOf(issue.assignee.id));
        }
        params.put("labels", TextUtils.join(",", issue.labels));
        params.put("milestone_id", String.valueOf(issue.milestone.id));
        String response = network.postFromUrlWithToken(url, params);
        return JSON.parseObject(response, Issue.class);
    }

    public List<Issue> getIssuesByMilestone(String projectId, Milestone milestone) {
        List<Issue> allIssue = getAllIssueByProjectId(projectId);
        ArrayList<Issue> result = new ArrayList<Issue>();
        for (Issue issue : allIssue) {
            if (issue.milestone != null && issue.milestone.id == milestone.id) {
                result.add(issue);
            }
        }
        return result;
    }

}
