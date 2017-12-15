package com.zzx.gank.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.mvp.presenter.TypePresenter;
import com.zzx.gank.mvp.view.ITypeView;
import com.zzx.gank.ui.activity.WebViewActivity;
import com.zzx.gank.ui.adatper.DailyAdapter;
import com.zzx.gank.ui.adatper.OnItemClickListener;
import com.zzx.gank.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zuozhixiang on 17/11/21.
 */

public class TypeFragment extends BaseFragment<TypePresenter> implements ITypeView, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,OnItemClickListener {

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String mType;
    private int mPageIndex = 1;
    private boolean mCanloading = true;
    private boolean mIsRefresh = true;


    private List<Gank> mDatas;
    private DailyAdapter mAdapter;

    public TypeFragment() {
    }

    public static TypeFragment newInstance(String type) {
        TypeFragment fragment = new TypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void initPresenter() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getString("type");
        }
        mPresenter = new TypePresenter(getActivity(), this);
        mPresenter.attachView();
        mPresenter.getData(mType, mPageIndex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void initView() {
        mDatas = new ArrayList<>();
        mAdapter = new DailyAdapter(getActivity(), mDatas);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadMoreListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getData(mType, mPageIndex);
            }
        });
    }

    @Override
    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        mCanloading = true;
    }

    @Override
    public void showNoMoreData() {
        mCanloading = false;

    }

    @Override
    public void showDataList(List<Gank> dataList) {
        mCanloading = true;
        if (mIsRefresh) {
            mDatas.clear();
            mDatas.addAll(dataList);
            mIsRefresh = false;
            mAdapter.notifyDataSetChanged();
        } else {
            mDatas.addAll(dataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mPageIndex = 1;
        mPresenter.getData(mType, mPageIndex);
    }

    @Override
    public void loadMore() {
        mPageIndex++;
        if (mCanloading) {
            mPresenter.getData(mType, mPageIndex);
            mCanloading = false;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mDatas != null && mDatas.size() > 0) {
            Gank gank = mDatas.get(position);
            if (gank != null) {
                WebViewActivity.show(getActivity(), gank.desc, gank.url);
            }
        }
    }
}
