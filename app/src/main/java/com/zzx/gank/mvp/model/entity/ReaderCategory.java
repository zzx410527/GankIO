package com.zzx.gank.mvp.model.entity;

/**
 * Created by zuozhixiang on 17/11/23.
 */

public class ReaderCategory extends Base {
    private String title;
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ReaderCategory{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
