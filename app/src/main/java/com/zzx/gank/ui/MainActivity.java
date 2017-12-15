package com.zzx.gank.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zzx.gank.R;
import com.zzx.gank.api.GankType;
import com.zzx.gank.ui.activity.AboutActivity;
import com.zzx.gank.ui.activity.BaseActivity;
import com.zzx.gank.ui.activity.ReaderActivity;
import com.zzx.gank.ui.fragment.AllFragment;
import com.zzx.gank.ui.fragment.TypeFragment;
import com.zzx.gank.ui.fragment.WelfareFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReaderActivity.show(MainActivity.this);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {

    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        switchFragment(GankType.ALL);
    }

    String currentFragmentTag;

    public void switchFragment(String name) {
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = mFragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment foundFragment = mFragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            if (name.equals(GankType.ALL)) {
                foundFragment = AllFragment.newInstance();
            } else if (name.equals(GankType.WELFARE)) {
                foundFragment = WelfareFragment.newInstance(name);
            } else {
                foundFragment = TypeFragment.newInstance(name);
            }
        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.container, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_all:
                switchFragment(GankType.ALL);
                setTitle(R.string.all);
                break;
            case R.id.nav_welfare:
                switchFragment(GankType.WELFARE);
                setTitle(R.string.fuli);
                break;
            case R.id.nav_android:
                switchFragment(GankType.ANDROID);
                setTitle(R.string.android);
                break;
            case R.id.nav_ios:
                switchFragment(GankType.IOS);
                setTitle(R.string.ios);
                break;
            case R.id.nav_front:
                switchFragment(GankType.FRONTEND);
                setTitle(R.string.front);
                break;
            case R.id.nav_video:
                switchFragment(GankType.VIDEO);
                setTitle(R.string.video);
                break;
            case R.id.nav_about:
                AboutActivity.show(this);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
