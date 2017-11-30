package com.mqunar.jonsnow.entity;

/**
 * Created by ironman.li on 2016/7/15.
 */
public class Issue {
    public int id;
    public int iid;
    public int project_id;
    public String title;
    public String description;
    public String state;
    public String created_at;
    public String updated_at;
    public String[] labels;
    public Milestone milestone;
    public User assignee;
    public User author;
}
