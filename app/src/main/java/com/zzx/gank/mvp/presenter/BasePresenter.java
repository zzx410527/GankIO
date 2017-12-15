package com.zzx.gank.mvp.presenter;

import android.content.Context;

import com.zzx.gank.mvp.view.IBaseView;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public abstract class BasePresenter<T extends IBaseView> {
    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void attachView() {
        if (iView != null) {
            iView.initView();
        }
    }


    public void detachView() {
        if (iView != null) {
            iView = null;
        }
    }
}
