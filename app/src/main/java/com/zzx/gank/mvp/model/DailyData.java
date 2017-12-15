package com.zzx.gank.mvp.model;

import com.google.gson.annotations.SerializedName;
import com.zzx.gank.mvp.model.entity.Base;
import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/23.
 */

public class DailyData extends Base {
    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> restVideoList;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> welfareList;
        @SerializedName("拓展资源")
        public List<Gank> expandResourcesList;
        @SerializedName("瞎推荐")
        public List<Gank> recommendList;
        @SerializedName("App")
        public List<Gank> appList;
        @SerializedName("前端")
        public List<Gank> frontList;
    }

    @Override
    public String toString() {
        return "DailyData{" +
                "category=" + category +
                ", results=" + results +
                '}';
    }
}
