package com.zzx.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Reader;
import com.zzx.gank.mvp.model.entity.ReaderCategory;
import com.zzx.gank.mvp.presenter.ReaderPresenter;
import com.zzx.gank.mvp.view.IReaderView;
import com.zzx.gank.ui.adatper.ReaderCategoryAdapter;

import java.util.List;

import butterknife.BindView;

public class ReaderActivity extends BaseActivity<ReaderPresenter> implements IReaderView {

    @BindView(R.id.tab_layout)
    TabLayout mTabLaout;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    private ReaderCategoryAdapter mReaderCategoryAdapter;


    public static void show(Context context) {
        Intent intent = new Intent(context, ReaderActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getlayoutId() {
        return R.layout.activity_reader;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ReaderPresenter(this, this);
        mPresenter.attachView();
        mPresenter.getReaderCategorys();
    }

    @Override
    public void initView() {
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showNoMoreData() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showReaderList(List<Reader> readers) {

    }

    @Override
    public void showReaderCategory(List<ReaderCategory> categories) {
        mReaderCategoryAdapter = new ReaderCategoryAdapter(getSupportFragmentManager(), categories);
        mViewPager.setAdapter(mReaderCategoryAdapter);
        mTabLaout.setupWithViewPager(mViewPager);
    }
}
