package com.zzx.gank.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.presenter.AllPresenter;
import com.zzx.gank.mvp.view.IAllView;
import com.zzx.gank.ui.adatper.DayItemAdapter;
import com.zzx.gank.ui.adatper.OnItemClickListener;
import com.zzx.gank.utils.DateUtil;
import com.zzx.gank.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zuozhixiang on 17/11/16.
 */

public class AllFragment extends BaseFragment<AllPresenter> implements IAllView, LoadMoreRecyclerView.LoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    private int mPageIndex = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;


    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    private DayItemAdapter mDayItemAdapter;
    private List<Gank> datas;

    public static AllFragment newInstance() {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new AllPresenter(getActivity(), this);
        mPresenter.attachView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        mDayItemAdapter = new DayItemAdapter(getActivity(), datas);
        mDayItemAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mDayItemAdapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                mPresenter.getData(mPageIndex);
            }
        });
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        Toast.makeText(getActivity(), getString(R.string.load_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDayItemList(List<Gank> alllList) {
        canLoading = true;
        if (isRefresh) {
            datas.clear();
            datas.addAll(alllList);
            isRefresh = false;
        } else {
            datas.addAll(alllList);
        }
        mDayItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        if (canLoading) {
            mPageIndex++;
            mPresenter.getData(mPageIndex);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mPageIndex = 1;
        mPresenter.getData(mPageIndex);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (datas != null && datas.size() > 0) {
            Gank gank = datas.get(position);
            if (gank != null) {
                mPresenter.showDailyActivity(getActivity(),view.findViewById(R.id.iv_gril), DateUtil.formatDate(gank.publishedAt),gank
                .getImgUrl());
            }
        }
    }
}
