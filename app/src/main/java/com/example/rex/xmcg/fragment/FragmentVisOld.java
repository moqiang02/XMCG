package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.VisOldAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.model.Vis;
import com.example.rex.xmcg.utils.SPUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Rex on 2016/10/10.
 */
public class FragmentVisOld extends android.support.v4.app.Fragment {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private VisOldAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vis, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        OkGo.post(URL.GET_RECORD)
                .tag(getActivity())
                .params("isFirst", (String) SPUtils.get(getContext(), "isFirst", ""))
                .params("patNumber", (String) SPUtils.get(getContext(), "patNumber", ""))
                .params("identity", (String) SPUtils.get(getContext(), "identity", ""))
                .params("type", "old")
                .execute(new DialogCallback<LzyResponse<List<Vis>>>(getActivity()) {

                    @Override
                    public void onSuccess(LzyResponse<List<Vis>> responseData, Call call, Response response) {
                        ArrayList<Vis> list = (ArrayList<Vis>) responseData.data;
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

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
