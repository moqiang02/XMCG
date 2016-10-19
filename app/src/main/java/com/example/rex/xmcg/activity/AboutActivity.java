package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.fragment.FragmentHealth;
import com.example.rex.xmcg.fragment.FragmentHome;
import com.example.rex.xmcg.fragment.FragmentHospital;
import com.example.rex.xmcg.fragment.FragmentLogin;
import com.example.rex.xmcg.fragment.FragmentMy;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    private View mDecorView;
    private CommonTabLayout mTabLayout;private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "健康", "医院", "我的"};
    private SlidingTabLayout tabLayout_4;
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("关于长庚");


        mDecorView = getWindow().getDecorView();        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        mFragments.add(new FragmentHome());
        mFragments.add(new FragmentHealth());
        mFragments.add(new FragmentHospital());
        mFragments.add(new FragmentMy());
        mFragments.add(new FragmentLogin());
        SlidingTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_4);
        tabLayout_4.setViewPager(vp);
    }
}
