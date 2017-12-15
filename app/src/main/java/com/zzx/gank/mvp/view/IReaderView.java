package com.zzx.gank.mvp.view;

import com.zzx.gank.mvp.model.entity.Reader;
import com.zzx.gank.mvp.model.entity.ReaderCategory;

import java.util.List;

/**
 * Created by zuozhixiang on 17/11/22.
 */

public interface IReaderView extends IBaseView {

    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showReaderList(List<Reader> readers);

    void showReaderCategory(List<ReaderCategory> categories);
}
