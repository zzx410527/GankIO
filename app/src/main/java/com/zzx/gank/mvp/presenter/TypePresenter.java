package com.zzx.gank.mvp.presenter;

import android.content.Context;

import com.zzx.gank.api.GankClient;
import com.zzx.gank.mvp.model.GankData;
import com.zzx.gank.mvp.view.ITypeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zuozhixiang on 17/11/21.
 */

public class TypePresenter extends BasePresenter<ITypeView> {
    public TypePresenter(Context context, ITypeView iView) {
        super(context, iView);
    }

    public void getData(String type, int page) {
        GankClient.getRetrofitService()
                .getGankByType(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData ganHuoData) {
                        if (iView != null) {
                            iView.hideProgress();
                        }


                        if (ganHuoData.results.size() == 0) {
                            if (iView != null) {
                                iView.showNoMoreData();
                            }
                        } else {
                            if (iView != null) {
                                iView.showDataList(ganHuoData.results);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null) {
                            iView.hideProgress();
                            iView.showErrorView();
                        }

                    }
                });
    }

}
