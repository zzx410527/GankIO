package com.zzx.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.zzx.gank.R;
import com.zzx.gank.ui.fragment.WebFragment;
import com.zzx.gank.utils.ClipboardUtils;
import com.zzx.gank.utils.MyLog;
import com.zzx.gank.utils.ShareUtils;

import butterknife.BindView;

/**
 * Created by zuozhixiang on 17/11/7.
 */

public class WebViewActivity extends BaseActivity {
    public static final String WEB_URL = "webViewUrl";
    public static final String TITLE = "webViewTitle";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_switcher)
    TextSwitcher mTextSwitcher;

    private String mUrl;
    private String mTitle;

    private FragmentManager mFragmentManager;
    private WebFragment mWebViewFragment;

    public static void show(Context context, String title, String url) {
        MyLog.d("title:" + title + " url:" + url);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void initView() {
        mUrl = getIntent().getExtras().getString(WEB_URL);
        mTitle = getIntent().getExtras().getString(TITLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                Context context = WebViewActivity.this;
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        mTextSwitcher.setText(mTitle);
        mTextSwitcher.setSelected(true);

        mFragmentManager = getSupportFragmentManager();
        mWebViewFragment = WebFragment.newInstance(mUrl);
        mFragmentManager.beginTransaction().replace(R.id.fl_content, mWebViewFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_copy) {
            ClipboardUtils.setText(this, mUrl);
            Snackbar.make(mToolbar, "已复制到剪切板", Snackbar.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            ShareUtils.shareText(this, "【" + mTitle + "】" + mUrl);
        } else if (id == R.id.action_browser) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(mUrl));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onBackPressed() {
        if (mWebViewFragment.canGoBack()) {
            mWebViewFragment.goBack();
        } else {
            finish();
        }
    }
}
