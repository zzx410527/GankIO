package com.zzx.gank.ui.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zzx.gank.R;
import com.zzx.gank.mvp.model.entity.Reader;
import com.zzx.gank.utils.ImageLoadHelper;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/23.
 */

public class ReaderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Reader> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public ReaderAdapter(Context context, List<Reader> list) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.item_reader);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Reader reader = mList.get(position);
        holder.setText(R.id.tv_title, reader.getTitle());
        holder.setText(R.id.tv_source, reader.getSource());
        holder.setText(R.id.tv_time, reader.getTime());
        holder.setImageUrl(R.id.iv_avatar, reader.getSourceAvatar());
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
