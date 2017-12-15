package com.zzx.gank.ui.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/22.
 */

public class WelfareAdapter extends  RecyclerView.Adapter<ViewHolder>  {
    private List<Gank> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> mHeights;


    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public WelfareAdapter(Context context, List<Gank> list) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.item_grid);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Gank gank = mList.get(position);
        holder.setImageUrl(R.id.iv_gril, gank.url);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
