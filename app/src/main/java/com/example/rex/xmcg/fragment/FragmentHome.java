package com.example.rex.xmcg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.activity.DepartmentActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentHome extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.bespeak, R.id.report})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case R.id.bespeak:
                startActivity(new Intent(getContext(),DepartmentActivity.class));
                break;
            case R.id.report:
                Log.e("hm", "red3");
                break;
        }
    }
}
