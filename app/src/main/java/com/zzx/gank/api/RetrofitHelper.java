package com.zzx.gank.api;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class RetrofitHelper {

    private static SparseArray<RetrofitHelper> mRetrofitHelpers = new SparseArray<>();


    private RetrofitHelper(int hostType) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(GankApi.getBaseUrl(hostType))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private Retrofit mRetrofit;

    public static RetrofitHelper getInstansce(int hostType) {
        RetrofitHelper retrofitHelper = mRetrofitHelpers.get(hostType);
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper(hostType);
            mRetrofitHelpers.put(hostType, retrofitHelper);
            return retrofitHelper;
        }

        return retrofitHelper;
    }

    public GankService getRetrofitService() {
        return mRetrofit.create(GankService.class);
    }


}
