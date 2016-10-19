package com.example.rex.xmcg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.activity.AboutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentHospital extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospotal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.profiles, R.id.message, R.id.event})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case R.id.profiles:
                startActivity(new Intent(getContext(),AboutActivity.class));
                break;
            case R.id.message:
                Log.e("hm","tv_test3");
                break;
            case R.id.event:
                Log.e("hm","tv_test3");
                break;
        }
    }
}
