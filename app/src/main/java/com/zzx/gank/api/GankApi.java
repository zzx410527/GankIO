package com.zzx.gank.api;

/**
 * Created by zuozhixiang on 17/10/21.
 */

public class GankApi {
    public static final String BASE_GANK_URL = "http://gank.io/api/";

    public static final String READ_BASE_URL = "http://gank.io/xiandu/";

    public static final int GANK_HOST = 1;
    public static final int READ_HOST = 2;

    public static String getBaseUrl(int hostType) {
        if (hostType == READ_HOST) {
            return READ_BASE_URL;
        } else {
            return BASE_GANK_URL;
        }

    }

}
