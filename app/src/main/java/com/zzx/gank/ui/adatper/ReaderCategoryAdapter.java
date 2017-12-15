package com.zzx.gank.ui.adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zzx.gank.mvp.model.entity.ReaderCategory;
import com.zzx.gank.ui.fragment.ReaderFragment;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/23.
 */

public class ReaderCategoryAdapter extends FragmentStatePagerAdapter {
    private List<ReaderCategory> list;

    public ReaderCategoryAdapter(FragmentManager fm, List<ReaderCategory> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return ReaderFragment.newInstance(list.get(position).getCategory());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
