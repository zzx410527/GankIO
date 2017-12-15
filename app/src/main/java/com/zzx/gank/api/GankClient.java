package com.zzx.gank.api;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class GankClient {
    public static GankService getRetrofitService() {
        return RetrofitHelper.getInstansce(GankApi.GANK_HOST).getRetrofitService();
    }

    public static GankService getReadService(){
        return RetrofitHelper.getInstansce(GankApi.READ_HOST).getRetrofitService();
    }

}
