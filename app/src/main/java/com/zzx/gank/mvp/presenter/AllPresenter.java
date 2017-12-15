package com.zzx.gank.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.zzx.gank.api.GankClient;
import com.zzx.gank.mvp.model.GankData;
import com.zzx.gank.mvp.view.IAllView;
import com.zzx.gank.ui.activity.DailyActivity;
import com.zzx.gank.utils.MyLog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zuozhixiang on 17/11/16.
 */

public class AllPresenter extends BasePresenter<IAllView> {
    public AllPresenter(Context context, IAllView iView) {
        super(context, iView);
    }

    public void getData(int page) {
        iView.showProgress();
        GankClient.getRetrofitService().getRecently(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData dayItemData) throws Exception {
                        if (dayItemData.results == null || dayItemData.results.size() == 0) {
                            iView.showNoMoreData();
                        } else {
                            iView.showDayItemList(dayItemData.results);
                        }
                        iView.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        MyLog.d(throwable.getMessage());
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });
    }

    public void showDailyActivity(Activity context, View v, String date, String imageurl) {
        DailyActivity.start(context, v, date, imageurl);
    }
}
