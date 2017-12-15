package com.zzx.gank.mvp.view;

import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.model.entity.Gril;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public interface IMainView extends IBaseView {
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
    void showGrilList(List<Gank> grilList);
}
