package com.zzx.gank.api;

import com.zzx.gank.base.GlobalConfig;
import com.zzx.gank.mvp.model.DailyData;
import com.zzx.gank.mvp.model.GankData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public interface GankService {


    /**
     * @param category
     * @param page
     * @return
     */
    @GET("data/{category}/" + GlobalConfig.PAGE_SIZE + "/{page}")
    Observable<GankData> getGankByType(@Path("category") String category, @Path("page") int page);


    @GET("day/{year}/{month}/{day}")
    Observable<DailyData> getDailyData(@Path("year") String year, @Path("month") String mouth, @Path("day") String day);

    @GET("history/content/10/{pageIndex}")
    Observable<GankData> getRecently(@Path("pageIndex") int pageIndex);

    @GET("{category}/page/{page}")
    Observable<ResponseBody> getHtmlBody(@Path("category") String category, @Path("page") int pageindex);
    @GET(".")
    Observable<ResponseBody> getReaderCategory();

}
