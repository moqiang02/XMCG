package com.example.rex.xmcg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.activity.DepartmentActivity;
<<<<<<< HEAD
import com.example.rex.xmcg.activity.DoctorInfoActivity;
=======
import com.example.rex.xmcg.activity.DoctorIntroduceActivity;
>>>>>>> 23d7bb6ffee4ca1a07b919153838e68eff006f32
import com.example.rex.xmcg.weiget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentHome extends android.support.v4.app.Fragment {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        titleBar.setLeftImageResource(R.mipmap.icon_logo);
        titleBar.setTitle("厦门长庚医院");
        titleBar.addAction(new TitleBar.ImageAction(R.mipmap.message) {
            @Override
            public void performAction(View view) {
                Toast.makeText(getActivity(), "点击了收藏", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @OnClick({R.id.bespeak, R.id.report, R.id.doctor_info})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case R.id.bespeak:
                startActivity(new Intent(getContext(), DepartmentActivity.class));
                break;
            case R.id.report:
                Log.e("hm", "red3");
                break;
            case R.id.doctor_info:
                startActivity(new Intent(getContext(), DoctorInfoActivity.class));
                break;
        }
    }
}
