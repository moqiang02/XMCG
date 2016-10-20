package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.adapter.MyPagerAdapter;
import com.example.rex.xmcg.fragment.FragmentVis;
import com.example.rex.xmcg.fragment.FragmentVisOld;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BespeakActivity extends AppCompatActivity {
    private View mDecorView;
    private SlidingTabLayout mTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"待就诊", "历史就诊"};
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bespeak);
        ButterKnife.bind(this);

        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("我的预约");


        mDecorView = getWindow().getDecorView();
        mFragments.add(new FragmentVis());
        mFragments.add(new FragmentVisOld());
        ViewPager vp = ViewFindUtils.find(mDecorView, R.id.vp);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vp.setAdapter(mAdapter);
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl_4);
        mTabLayout.setViewPager(vp);
        vp.setCurrentItem(0);

    }
}
