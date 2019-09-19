package com.medsec.entity;

public class File {
    private String id;
    private String title;
    private String link;
    private String pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public File id(final String id) {
        this.id = id;
        return this;
    }

    public File title(final String title) {
        this.title = title;
        return this;
    }

    public File link(final String link) {
        this.link = link;
        return this;
    }

    public File pid(final String pid) {
        this.pid = pid;
        return this;
    }



}
