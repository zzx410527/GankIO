package com.zzx.gank.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzx.gank.R;
import com.zzx.gank.mvp.presenter.PicPresenter;
import com.zzx.gank.mvp.view.IPicView;
import com.zzx.gank.utils.ImageLoadHelper;
import com.zzx.gank.utils.SnackBarUtils;
import com.zzx.gank.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class PicAcitvity extends BaseActivity<PicPresenter> implements IPicView {

    @BindView(R.id.view_pager)
    MyViewPager mViewPager;
    @BindView(R.id.tv_index)
    TextView mTvIndex;

    @BindView(R.id.iv_download)
    ImageView mIvDownload;

    private List<String> mImageUrls;
    private int mCurrentIndex = 0;
    private MypagerAdapter mMypagerAdapter;

    public static void show(Context context, View view, ArrayList<String> imageurls, int index) {
        Intent intent = new Intent(context, PicAcitvity.class);
        intent.putStringArrayListExtra("urls", imageurls);
        intent.putExtra("index", index);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(context, intent,
                compat.toBundle());
    }

    public static void show(Context context, View view, String url) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        show(context, view, urls, 0);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_pic;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PicPresenter(this, this);
        mPresenter.attachView();
    }

    @Override
    public void initView() {
        mImageUrls = getIntent().getExtras().getStringArrayList("urls");
        mCurrentIndex = getIntent().getExtras().getInt("index");
        if (mCurrentIndex < 0) {
            mCurrentIndex = 0;
        }
        mTvIndex.setText((mCurrentIndex + 1) + "/" + mImageUrls.size());

        RxPermissions rxPermissions = new RxPermissions(this);
        RxView.clicks(mIvDownload)
                .compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull com.tbruyelle.rxpermissions2.Permission permission) throws Exception {
                        if (permission.granted) {
                            mPresenter.downloadImage(PicAcitvity.this, mImageUrls.get(mCurrentIndex));
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {
                            SnackBarUtils.makeShort(mViewPager, getString(R.string.no_permisstion_write))
                                    .info();
                        }
                    }
                });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mTvIndex.setText((mCurrentIndex + 1) + "/" + mImageUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mMypagerAdapter = new MypagerAdapter();
        mViewPager.setAdapter(mMypagerAdapter);
        mViewPager.setCurrentItem(mCurrentIndex);
    }

    @Override
    public void saveScuess(String text) {
        SnackBarUtils.makeLong(mViewPager, "已保存至相册").info();
    }

    @Override
    public void saveErr(String text) {
        SnackBarUtils.makeLong(mViewPager, "保存失败:" + text).danger();
    }

    class MypagerAdapter extends PagerAdapter {

        public MypagerAdapter() {
        }

        @Override
        public int getCount() {
            return mImageUrls == null ? 0 : mImageUrls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageLoadHelper.get().displayImage(photoView, mImageUrls.get(position));
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
