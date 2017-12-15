package com.zzx.gank.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.zzx.gank.R;
import com.zzx.gank.mvp.view.IPicView;
import com.zzx.gank.utils.FileUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zuozhixiang on 17/11/20.
 */

public class PicPresenter extends BasePresenter<IPicView> {
    public PicPresenter(Context context, IPicView iView) {
        super(context, iView);
    }

    public void downloadImage(final Context context, final String url) {
        final String path = FileUtils.getSaveImagePath(context) + File.separator + FileUtils.getFileName(url);
        Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(ObservableEmitter<Uri> e) throws Exception {
                Uri uri = FileUtils.download(url, path);
                if (uri == null) {
                    e.onError(new Exception(context.getResources().getString(R.string.save_err)));
                } else {
                    e.onNext(uri);
                    e.onComplete();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        iView.saveScuess(context.getResources().getString(R.string.save_success));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        iView.saveErr(context.getResources().getString(R.string.save_err));
                    }
                });

    }
}
