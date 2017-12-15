package com.zzx.gank.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zzx.gank.R;
import com.zzx.gank.api.GankType;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.presenter.DailyPresenter;
import com.zzx.gank.mvp.view.IDailyView;
import com.zzx.gank.ui.adatper.DailyAdapter;
import com.zzx.gank.ui.adatper.OnItemClickListener;
import com.zzx.gank.utils.ImageLoadHelper;
import com.zzx.gank.utils.NetWorkUtil;
import com.zzx.gank.utils.SnackBarUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyActivity extends BaseActivity<DailyPresenter> implements IDailyView, OnItemClickListener {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.iv_head)
    ImageView mIvHeadView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecylerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeLayout;


    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    private String dateStr;

    private DailyAdapter mDailyAdapter;

    private List<Gank> mDatas;
    private String mImageUrl;

    public static void start(Context context, String date, String imageUrl) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);
    }

    public static void start(Activity activity, View v, String date, String imageUrl) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                v,
                activity.getResources().getString(R.string.image_transition));

        Intent intent = new Intent(activity, DailyActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("imageUrl", imageUrl);

        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DailyPresenter(this, this);
        mPresenter.attachView();
    }

    @Override
    public void initView() {

        dateStr = getIntent().getExtras().getString("date");
        mImageUrl = getIntent().getExtras().getString("imageUrl");
        String[] dates = null;
        if (dateStr.contains("-")) {
            dates = getIntent().getExtras().getString("date").split("-");
        } else if (dateStr.contains("/")) {
            dates = getIntent().getExtras().getString("date").split("/");
        }
        mPresenter.getDailyData(dates[0], dates[1], dates[2]);
        mDatas = new ArrayList<>();
        mDailyAdapter = new DailyAdapter(this, mDatas);
        mDailyAdapter.setOnItemClickListener(this);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setAdapter(mDailyAdapter);
        mSwipeLayout.setEnabled(false);
        ImageLoadHelper.get().displayImage(mIvHeadView, mImageUrl);
        ViewCompat.setTransitionName(mIvHeadView, getResources().getString(R.string.image_transition));
        mIvHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showPicView(DailyActivity.this, v, mImageUrl);
            }
        });

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitle(dateStr);
        boldTitleText(mCollapsingToolbarLayout);

    }

    private void boldTitleText(CollapsingToolbarLayout mCollapsingToolbarLayout) {
        try {

            Class aClass = mCollapsingToolbarLayout.getClass();
            Field mCollapsingTextHelper = aClass.getDeclaredField("mCollapsingTextHelper");
            mCollapsingTextHelper.setAccessible(true);
            Object textHelper = mCollapsingTextHelper.get(mCollapsingToolbarLayout);
            aClass = textHelper.getClass();
            Field mTextPaint = aClass.getDeclaredField("mTextPaint");
            mTextPaint.setAccessible(true);
            TextPaint textpaint = (TextPaint) mTextPaint.get(textHelper);
            textpaint.setFakeBoldText(true);
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.fab)
    void fabClick() {
        if (!NetWorkUtil.isWifiConnected(this)) {
            Snackbar.make(mFab, "你使用的不是wifi网络，要继续吗？", Snackbar.LENGTH_LONG).setAction("继续",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mDatas.size() > 0 && mDatas.get(0).type.equals(GankType.VIDEO)) {
                                WebViewActivity.show(DailyActivity.this, mDatas.get(0).desc, mDatas.get(0).url);
                            } else {
                                SnackBarUtils.makeLong(mFab, "出了点小问题").show();
                            }
                        }
                    });
        } else {
            if (mDatas.size() > 0 && mDatas.get(0).type.equals(GankType.VIDEO)) {
                WebViewActivity.show(DailyActivity.this, mDatas.get(0).desc, mDatas.get(0).url);
            } else {
                SnackBarUtils.makeLong(mFab, "出了点小问题").show();
            }
        }

    }


    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoMoreData() {
        Toast.makeText(this, getString(R.string.no_more_data), Toast.LENGTH_LONG).show();
    }


    @Override
    public void showDailyList(List<Gank> dailyList) {
        mDatas.addAll(dailyList);
        mDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mDatas != null && mDatas.size() > 0) {
            Gank gank = mDatas.get(position);
            if (gank != null) {
                WebViewActivity.show(this, gank.desc, gank.url);
            }
        }
    }
}
