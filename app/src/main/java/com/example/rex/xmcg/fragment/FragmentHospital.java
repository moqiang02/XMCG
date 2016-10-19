package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rex.xmcg.R;

import butterknife.ButterKnife;

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
}
