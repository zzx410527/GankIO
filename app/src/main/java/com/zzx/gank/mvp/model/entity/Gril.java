package com.zzx.gank.mvp.model.entity;

import com.zzx.gank.mvp.model.entity.Base;

import java.util.Date;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class Gril extends Base {
    public boolean used;
    public String type;//干货类型，如Android，iOS，福利等
    public String url;//链接地址
    public String who;//作者
    public String desc;//干货内容的描述
    public Date createdAt;
    public Date updatedAt;
    public Date publishedAt;

    @Override
    public String toString() {
        return "Meizi{" +
                "who='" + who + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
