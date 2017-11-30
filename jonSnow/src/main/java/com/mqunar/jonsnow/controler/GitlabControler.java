package com.mqunar.jonsnow.controler;

import com.alibaba.fastjson.JSON;
import com.mqunar.jonsnow.entity.CrashDetail;
import com.mqunar.jonsnow.entity.Issue;
import com.mqunar.jonsnow.entity.Milestone;
import com.mqunar.jonsnow.service.gitlab.IssueService;
import com.mqunar.jonsnow.service.gitlab.MilestoneService;
import com.mqunar.jonsnow.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class GitlabControler {

    public static final String SEPARATOR_CRASHID_ISSUETITLE = "-->";


    public void createIssues(int atomVersion ,String projectId, List<CrashDetail> filteredCrash) {
        MilestoneService milestoneService = new MilestoneService();
        Milestone milestone = milestoneService.getMilestoneByVersion(atomVersion);
        System.out.println(JSON.toJSONString(milestone));
        assertNotNull(milestone, "mileston is null");
        IssueService issueService = new IssueService();
        List<Issue> createdIssue = issueService.getIssuesByMilestone(projectId, milestone);
        List<Issue> newIssue = getNewIssue(filteredCrash, createdIssue, milestone);
        System.out.println("new issue is ");
        System.out.println(JSON.toJSONString(newIssue));
//        issueService.createNewIssues(projectId, newIssue);
    }

    private List<Issue> getNewIssue(List<CrashDetail> filteredCrash, List<Issue> createdIssue, Milestone milestone) {
        if (filteredCrash == null || filteredCrash.size() == 0) {
            return null;
        }
        if (createdIssue == null || createdIssue.size() == 0) {
            ArrayList<Issue> issues = new ArrayList<Issue>();
            for (CrashDetail crashDetail : filteredCrash) {
                issues.add(getIssueFromCrash(crashDetail,milestone));
            }
            return issues;
        } else {
            ArrayList<Issue> issues = new ArrayList<Issue>();
            for (CrashDetail crashDetail : filteredCrash) {
                int mark = 0;
                for (Issue issue : createdIssue) {
                    String[] strs = issue.title.split(SEPARATOR_CRASHID_ISSUETITLE);
                    String crashId = strs.length > 1 ? strs[1] : "";
                    if (crashDetail.crashId.equals(crashId)) {
                        mark = 1;
                        break;
                    }
                }
                if (mark == 0) {
                    issues.add(getIssueFromCrash(crashDetail, milestone));
                }
            }
            return issues;
        }
    }

    private Issue getIssueFromCrash(CrashDetail crashDetail, Milestone milestone) {
        Issue issue = new Issue();
        String title = "";
        if (TextUtils.isNotEmpty(crashDetail.signatureDigest)) {
            title = crashDetail.signatureDigest;
        } else if (TextUtils.isNotEmpty(crashDetail.signatureFull)) {
            title = crashDetail.signatureFull;
        }
        if (title.contains(":")) {
            title = title.split(":")[0];
        }
        title = title + SEPARATOR_CRASHID_ISSUETITLE + crashDetail.crashId;
        issue.title = title;
        issue.labels = new String[]{"onlineCrash","autoCreated"};
        issue.description = "### crash count:"+crashDetail.count + "\n\n";
        issue.description += "acra url : http://acra.corp.qunar.com/acralyzer/_design/acralyzer/index.html?token=JVU2NzRFJVU2RDlCJmlyb25tYW4ubGk=&id=ironman.li#/report-details/android-flight/" + crashDetail.url + "\n\n";
        issue.description += "> "+ TextUtils.join("\nat",crashDetail.key.split("at "));
        issue.description += TextUtils.join("\n\n",crashDetail.stackTrace);
        issue.milestone = milestone;
        return issue;
    }


    private void assertNotNull(Object object, String msg) {
        if (object == null) {
            throw new NullPointerException(msg);
        }
    }


}
