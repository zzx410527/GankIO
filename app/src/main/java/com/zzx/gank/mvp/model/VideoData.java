package com.zzx.gank.mvp.model;

import com.zzx.gank.mvp.model.entity.Base;
import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class VideoData extends Base {
    public List<Gank> results;

    @Override
    public String toString() {
        return "VideoData{" +
                "results=" + results +
                '}';
    }
}
