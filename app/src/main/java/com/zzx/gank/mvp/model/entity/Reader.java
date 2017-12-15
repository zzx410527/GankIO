package com.zzx.gank.mvp.model.entity;

/**
 * Created by zuozhixiang on 17/11/22.
 */

public class Reader {
    private String title;
    private String url;
    private String time;
    private String source;
    private String sourceAvatar;

    public String getSourceAvatar() {
        return sourceAvatar;
    }

    public void setSourceAvatar(String sourceAvatar) {
        this.sourceAvatar = sourceAvatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", source='" + source + '\'' +
                ", sourceAvatar='" + sourceAvatar + '\'' +
                '}';
    }
}
