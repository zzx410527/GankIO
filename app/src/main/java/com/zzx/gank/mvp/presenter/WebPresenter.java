package com.zzx.gank.mvp.presenter;

import android.content.Context;

import com.zzx.gank.mvp.view.IWebView;

/**
 * Created by zuozhixiang on 17/11/7.
 */

public class WebPresenter extends BasePresenter<IWebView> {
    public WebPresenter(Context context, IWebView iView) {
        super(context, iView);
    }
    public void  RequestData(){
        iView.showProgress();
    }
}
