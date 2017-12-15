package com.zzx.gank.ui.adatper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.utils.DateUtil;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/16.
 */

public class DayItemAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<Gank> list;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public DayItemAdapter(Context context, List<Gank> l) {
        this.mContext = context;
        list = l;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_dayitem, parent, false);
        return ViewHolder.createViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Gank dayItemData = list.get(position);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        ImageView view = holder.getView(R.id.iv_gril);
        view.setBackgroundColor(Color.argb(204, red, green, blue));
        holder.setImageUrl(R.id.iv_gril, dayItemData.getImgUrl());
        holder.setText(R.id.tv_desc, dayItemData.title);
        holder.setVisible(R.id.tv_who, false);
        holder.setText(R.id.tv_time, DateUtil.formatDate(dayItemData.publishedAt));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
