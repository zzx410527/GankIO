package com.zzx.gank.ui.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Gank;
import com.zzx.gank.utils.StringUtils;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/17.
 */

public class DailyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Gank> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public DailyAdapter(Context context, List<Gank> list) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.item_gank);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Gank gank = mList.get(position);
        if (position == 0) {
            showCategory(true, holder);
        } else {
            if (gank.type.equals(mList.get(position - 1).type)) {
                showCategory(false, holder);
            } else {
                showCategory(true, holder);
            }
        }
        holder.setText(R.id.tv_category, gank.type);
        holder.setText(R.id.tv_gank_desc, StringUtils.getGankStyleStr(mContext, gank));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    private void showCategory(boolean show, ViewHolder viewHolder) {
        viewHolder.setVisible(R.id.tv_category, show);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
