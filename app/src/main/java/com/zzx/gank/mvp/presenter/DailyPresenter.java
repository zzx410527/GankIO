package com.zzx.gank.mvp.presenter;

import android.content.Context;
import android.view.View;

import com.zzx.gank.api.GankClient;
import com.zzx.gank.mvp.model.DailyData;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.view.IDailyView;
import com.zzx.gank.ui.activity.PicAcitvity;
import com.zzx.gank.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zuozhixiang on 17/11/17.
 */

public class DailyPresenter extends BasePresenter<IDailyView> {
    public DailyPresenter(Context context, IDailyView iView) {
        super(context, iView);
    }

    public void getDailyData(String year, String month, String day) {
        iView.showProgress();
        GankClient.getRetrofitService()
                .getDailyData(year, month, day)
                .map(new Function<DailyData, List<Gank>>() {

                    @Override
                    public List<Gank> apply(DailyData dailyData) throws Exception {
                        List<Gank> ganks = addAlldailyData(dailyData);
                        if (MyLog.isShow()) {
                            for (Gank g : ganks) {
                                MyLog.d(g.toString() + "\n");
                            }
                        }
                        return ganks;
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(List<Gank> ganks) throws Exception {
                        if (ganks.size() == 0) {
                            iView.showNoMoreData();
                        } else {
                            iView.showDailyList(ganks);
                        }
                        iView.hideProgress();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });
    }

    private List<Gank> addAlldailyData(DailyData dailyData) {
        List<Gank> mGankList = new ArrayList<>();
        if (dailyData.results.androidList != null) mGankList.addAll(dailyData.results.androidList);

        if (dailyData.results.iOSList != null) mGankList.addAll(dailyData.results.iOSList);
        if (dailyData.results.frontList != null) mGankList.addAll(dailyData.results.frontList);
        if (dailyData.results.appList != null) mGankList.addAll(dailyData.results.appList);
        if (dailyData.results.expandResourcesList != null)
            mGankList.addAll(dailyData.results.expandResourcesList);
        if (dailyData.results.recommendList != null)
            mGankList.addAll(dailyData.results.recommendList);
        if (dailyData.results.restVideoList != null)
            mGankList.addAll(0, dailyData.results.restVideoList);
        return mGankList;
    }

    public void showPicView(Context context, View v, String url) {
        PicAcitvity.show(context, v, url);
    }

}
