package com.zzx.gank.base;

import com.zzx.gank.mvp.model.GrilData;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class DataManager {
    private DataManager mDaTaManger;

    private DataManager(){

    }
    public DataManager getInstansce(){
        if(mDaTaManger ==null){
            mDaTaManger = new DataManager();
        }
        return mDaTaManger;
    }




}
