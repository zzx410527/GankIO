package com.zzx.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.TextView;

import com.zzx.gank.BuildConfig;
import com.zzx.gank.R;
import com.zzx.gank.mvp.presenter.AboutPresenter;
import com.zzx.gank.mvp.view.IBaseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zuozhixiang on 17/11/30.
 */

public class AboutActivity extends BaseActivity<AboutPresenter> implements IBaseView {
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @OnClick(R.id.fab)
    void fabClick(){
        mPresenter.starInMarket();
    }



    public static void show(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override

    protected int getlayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new AboutPresenter(this, this);
        mPresenter.attachView();
    }

    @Override
    public void initView() {
        toolbarLayout.setTitle(getString(R.string.about_app));
        tvAppVersion.setText(String.format(getString(R.string.version), BuildConfig.VERSION_NAME));
    }
}
