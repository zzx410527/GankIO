package com.zzx.gank.mvp.model;

import com.zzx.gank.mvp.model.entity.Base;
import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/23.
 */

public class GankData extends Base {
    public boolean error;
    public List<Gank> results;

    @Override
    public String toString() {
        return "GankData{" +
                ", results=" + results +
                '}';
    }
}
