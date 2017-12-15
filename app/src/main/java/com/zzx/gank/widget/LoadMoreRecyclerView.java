package com.zzx.gank.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by zuozhixiang on 17/11/16.
 * 添加加载更多功能
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private boolean isScrollingToBottom = true;

    public interface LoadMoreListener {
        void loadMore();
    }

    private LoadMoreListener listener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.listener = loadMoreListener;
    }

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = null;

                layoutManager = (LinearLayoutManager) getLayoutManager();
                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
                int[] lastCompletelyVisibleItemPositions = manager.findLastCompletelyVisibleItemPositions(null);
                if (lastCompletelyVisibleItemPositions[manager.getSpanCount() - 1] == manager.getItemCount()
                        && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }

            } else if (getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager manager = (GridLayoutManager) getLayoutManager();
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }

            }

        }
    }
}
