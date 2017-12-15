package com.zzx.gank.mvp.view;

import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/21.
 */

public interface ITypeView extends IBaseView {
    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showDataList(List<Gank> grilList);
}
