package com.example.rex.xmcg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.activity.BespeakActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentMy extends android.support.v4.app.Fragment {
    @BindView(R.id.family_num)
    protected TextView family_num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        family_num.setText("33");
        return view;
    }

    @OnClick({R.id.bespeak, R.id.report})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case R.id.bespeak:
                startActivity(new Intent(getContext(),BespeakActivity.class));
                break;
            case R.id.report:
                Log.e("hm", "red");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
