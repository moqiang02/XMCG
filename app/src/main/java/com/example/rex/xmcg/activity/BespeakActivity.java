package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.model.Tab;
import com.example.rex.xmcg.fragment.FragmentVis;
import com.example.rex.xmcg.fragment.FragmentVisOld;
import com.example.rex.xmcg.weiget.TitleBar;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BespeakActivity extends AppCompatActivity {
    private View mDecorView;
    private CommonTabLayout mTabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"待就诊", "历史就诊"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_health_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_health_select};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
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

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new Tab(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mFragments.add(new FragmentVis());
        mFragments.add(new FragmentVisOld());
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl_4);
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

    }
}
