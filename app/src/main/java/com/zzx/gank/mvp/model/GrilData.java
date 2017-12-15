package com.zzx.gank.mvp.model;

import com.zzx.gank.mvp.model.entity.Base;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.model.entity.Gril;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/21.
 * http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
 */

public class GrilData extends Base {
    public List<Gank> results;

    @Override
    public String toString() {
        return "GrilData{" +
                "results=" + results +
                '}';
    }
}
