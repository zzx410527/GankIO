package com.zzx.gank.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Reader;
import com.zzx.gank.mvp.model.entity.ReaderCategory;
import com.zzx.gank.mvp.presenter.ReaderPresenter;
import com.zzx.gank.mvp.view.IReaderView;
import com.zzx.gank.ui.activity.WebViewActivity;
import com.zzx.gank.ui.adatper.OnItemClickListener;
import com.zzx.gank.ui.adatper.ReaderAdapter;
import com.zzx.gank.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ReaderFragment extends BaseFragment<ReaderPresenter> implements IReaderView, OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    private static final String ARG_PARAM1 = "category";

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mCategory;
    private int mPageIndex = 1;
    private ReaderAdapter mReaderAdapter;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private List<Reader> datas;


    public ReaderFragment() {
    }


    public static ReaderFragment newInstance(String category) {
        ReaderFragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ReaderPresenter(getActivity(), this);
        mPresenter.attachView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reader;
    }


    @Override
    public void initView() {
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARAM1);
        }
        datas = new ArrayList<>();
        mReaderAdapter = new ReaderAdapter(getActivity(), datas);
        mReaderAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mReaderAdapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        mPresenter.getReaderData(mCategory, mPageIndex);
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
    public void showReaderList(List<Reader> readers) {
        canLoading = true;
        if (isRefresh) {
            datas.clear();
            datas.addAll(readers);
            isRefresh = false;
        } else {
            datas.addAll(readers);
        }
        mReaderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReaderCategory(List<ReaderCategory> categories) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (datas != null && datas.size() > 0) {
            Reader reader = datas.get(position);
            if (reader != null) {
                WebViewActivity.show(getActivity(), reader.getSource(), reader.getUrl());
            }
        }
    }

    @Override
    public void loadMore() {
        if (canLoading) {
            mPageIndex++;
            mPresenter.getReaderData(mCategory, mPageIndex);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mPageIndex = 1;
        mPresenter.getReaderData(mCategory, mPageIndex);
    }

}
