package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.model.Tab;
import com.example.rex.xmcg.fragment.FragmentHealth;
import com.example.rex.xmcg.fragment.FragmentHome;
import com.example.rex.xmcg.fragment.FragmentHospital;
import com.example.rex.xmcg.fragment.FragmentLogin;
import com.example.rex.xmcg.fragment.FragmentMy;
import com.example.rex.xmcg.utils.SPUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CommonTabLayout mTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "健康", "医院", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_health_unselect,
            R.mipmap.tab_hospital_unselect, R.mipmap.tab_my_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_health_select,
            R.mipmap.tab_hospital_select, R.mipmap.tab_my_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new Tab(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mFragments.add(new FragmentHome());
        mFragments.add(new FragmentHealth());
        mFragments.add(new FragmentHospital());
        mFragments.add(new FragmentMy());
        mFragments.add(new FragmentLogin());
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl_1);
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 3) {
                    if (!(boolean) SPUtils.get(getApplicationContext(), "isLogin", false)) {
                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction ft = mFragmentManager.beginTransaction();
                        ft.hide(mFragments.get(0));
                        ft.hide(mFragments.get(1));
                        ft.hide(mFragments.get(2));
                        ft.hide(mFragments.get(3));
                        ft.show(mFragments.get(4));
                        ft.commit();
                    }

                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

}
