package com.zzx.gank.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzx.gank.mvp.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayoutId());
        ButterKnife.bind(this);
        initPresenter();
    }

    protected abstract int getlayoutId();

    protected abstract void initPresenter();
}
