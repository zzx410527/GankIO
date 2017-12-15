package com.zzx.gank.mvp.model.entity;

import com.zzx.gank.mvp.model.entity.Base;

import java.util.Date;
import java.util.List;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class Gank extends Base {
    public String _id;
    public boolean used;
    public String type; // 干货类型，如Android，iOS，福利等
    public String url; // 链接地址
    public String who; // 作者
    public String desc; // 干货内容的描述
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;
    public String source; // 来源
    public List<String> images;
    public String content;
    public String title;
    public String getImgUrl() {
        int start = content.indexOf("src=\"") + 5;

        int jpgEnd = content.indexOf(".jpg");
        int end = jpgEnd + 4;
        if (jpgEnd == -1) {
            jpgEnd = content.indexOf(".jpeg");
            end = jpgEnd + 5;
        }
        if (jpgEnd == -1) {
            jpgEnd = content.indexOf(".png");
            end = jpgEnd + 4;
        }
        if (end > start)
            return content.substring(start, end);
        else
            return "";
    }


    @Override
    public String toString() {
        return "Gank{" +
                "_id=" + _id + '\'' +
                ", used=" + used +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                ", source=" + source +
                '}';
    }
}
