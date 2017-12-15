package com.zzx.gank.mvp.presenter;

import android.content.Context;

import com.zzx.gank.api.GankClient;
import com.zzx.gank.mvp.model.entity.Reader;
import com.zzx.gank.mvp.model.entity.ReaderCategory;
import com.zzx.gank.mvp.view.IReaderView;
import com.zzx.gank.utils.MyLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by zuozhixiang on 17/11/22.
 */

public class ReaderPresenter extends BasePresenter<IReaderView> {
    public ReaderPresenter(Context context, IReaderView iView) {
        super(context, iView);
    }

    public void getReaderCategorys() {
        iView.showProgress();
        GankClient.getReadService().getReaderCategory()
                .map(new Function<ResponseBody, Document>() {
                    @Override
                    public Document apply(ResponseBody responseBody) throws Exception {
                        return Jsoup.parse(responseBody.string());
                    }
                })
                .map(new Function<Document, List<ReaderCategory>>() {
                    @Override
                    public List<ReaderCategory> apply(Document document) throws Exception {
                        List<ReaderCategory> list = new ArrayList<>();
                        Elements elements = document.body().getElementById("xiandu_cat").getElementsByTag("a");
                        for (Element element : elements) {
                            ReaderCategory item = new ReaderCategory();
                            item.setTitle(element.text());
                            item.setCategory(element.attr("href").substring(element.attr("href").lastIndexOf("/") + 1));
                            list.add(item);
                            MyLog.d("item:" + item.toString());
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ReaderCategory>>() {
                    @Override
                    public void accept(List<ReaderCategory> categories) throws Exception {
                        if (iView != null) {
                            iView.hideProgress();
                            iView.showReaderCategory(categories);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null)
                            iView.hideProgress();
                    }
                });
        ;

    }


    public void getReaderData(String category, int pageIndex) {
        if ("xiandu".equals(category)) {//处理默认页
            category = "wow";
        }
        if (pageIndex == 1) {
            iView.showProgress();
        }
        GankClient.getReadService().getHtmlBody(category, pageIndex)
                .map(new Function<ResponseBody, Document>() {
                    @Override
                    public Document apply(ResponseBody responseBody) throws Exception {
                        return Jsoup.parse(responseBody.string());
                    }
                })
                .map(new Function<Document, List<Reader>>() {
                    @Override
                    public List<Reader> apply(Document document) throws Exception {
                        List<Reader> list = new ArrayList<>();
                        Elements items = document.body().getElementsByClass("xiandu_item");
                        for (Element element : items) {
                            String title = element.getElementsByClass("site-title").text();
                            String url = element.getElementsByClass("site-title").attr("href");
                            String time = element.getElementsByTag("small").text();
                            String source = element.getElementsByClass("site-name").get(0).attr("title");
                            String sourceAvatar = element.getElementsByClass("site-name").get(0)
                                    .getElementsByTag("img").get(0).attr("src");
                            Reader item = new Reader();
                            item.setTitle(title);
                            item.setUrl(url);
                            item.setSource(source);
                            item.setTime(time);
                            item.setSourceAvatar(sourceAvatar);
                            list.add(item);
                            MyLog.d("item:" + item.toString());
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Reader>>() {
                    @Override
                    public void accept(List<Reader> readers) throws Exception {
                        if (iView != null) {
                            iView.hideProgress();
                            iView.showReaderList(readers);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null) {
                            iView.hideProgress();
                        }
                    }
                });
    }


}
