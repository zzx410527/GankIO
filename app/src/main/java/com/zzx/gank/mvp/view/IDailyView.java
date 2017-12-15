package com.zzx.gank.mvp.view;

import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/17.
 */

public interface IDailyView extends IBaseView {
    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showDailyList(List<Gank> dailyList);
}
