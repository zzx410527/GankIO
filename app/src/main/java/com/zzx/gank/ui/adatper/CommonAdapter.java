package com.zzx.gank.ui.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zzx.gank.R;

import java.util.List;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }


    protected List<T> mDatas;
    protected Context mContext;
    protected OnItemClickListener mOnItemClickListener;

    public CommonAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, R.layout.common_list_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public void convert(ViewHolder holder, T t) {
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }
}
