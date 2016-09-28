package com.example.rex.xmcg;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mTabLayout_1 = ViewFindUtils.find(mDecorView, R.id.tl_1);
        mTabLayout_1.setTabData(mTabEntities);
    }
}
