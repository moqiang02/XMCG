package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.VisOldAdapter;
import com.example.rex.xmcg.model.ResultBean;
import com.example.rex.xmcg.model.Vis;
import com.example.rex.xmcg.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/10.
 */
public class FragmentVisOld extends android.support.v4.app.Fragment {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private VisOldAdapter adapter;
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vis, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        RequestParams params = new RequestParams(URL.GET_RECORD);
        params.addQueryStringParameter("isFirst", (String) SPUtils.get(getContext(), "isFirst", ""));
        params.addQueryStringParameter("patNumber", (String) SPUtils.get(getContext(), "patNumber", ""));
        params.addQueryStringParameter("identity", (String) SPUtils.get(getContext(), "identity", ""));
        params.addQueryStringParameter("type", "old");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.json(result);
                java.lang.reflect.Type type = new TypeToken<ResultBean<Vis>>() {
                }.getType();
                ResultBean bean = gson.fromJson(result, type);
                ArrayList<Vis> list = (ArrayList<Vis>) bean.dataList;

                if (bean.code == 200) {
                    // 设置LinearLayoutManager
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    // 设置ItemAnimator
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    // 设置固定大小
                    mRecyclerView.setHasFixedSize(true);
                    // 初始化自定义的适配器
                    adapter = new VisOldAdapter(getContext(), list);
                 // 为mRecyclerView设置适配器
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
