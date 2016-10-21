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
import com.example.rex.xmcg.adapter.VisAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.CRegister;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.model.Vis;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.utils.TUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Rex on 2016/10/10.
 */
public class FragmentVis extends android.support.v4.app.Fragment {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private VisAdapter adapter;
    private ArrayList<Vis> listVis;

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
                .params("type", "new")
                .execute(new DialogCallback<LzyResponse<List<Vis>>>(getActivity()) {

                    @Override
                    public void onSuccess(LzyResponse<List<Vis>> responseData, Call call, Response response) {
                        listVis = (ArrayList<Vis>) responseData.data;
                        // 设置LinearLayoutManager
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        // 设置ItemAnimator
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        // 设置固定大小
                        mRecyclerView.setHasFixedSize(true);
                        // 初始化自定义的适配器
                        adapter = new VisAdapter(getContext(), listVis);
                        adapter.setOnItemClickListener(new VisAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Vis vis) {
                                cancelRegister((String) SPUtils.get(getContext(), "patNumber", ""), (String) SPUtils.get(getContext(), "identity", ""),
                                        vis.opdDate, vis.deptID, vis.opdTimeID, vis.doctorID, CommonUtils.getIPAddress(getContext()));
                            }

                        });
                        // 为mRecyclerView设置适配器
                        mRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.d(e.getMessage());
                        TUtils.showLong(e.getMessage());
                    }
                });


    }


    private void cancelRegister(String patNumber, String identity, final String opdDate, final String deptID, final String opdTimeID, final String doctorID, String ip) {

        OkGo.post(URL.CANCEL_REGISTER)
                .tag(getActivity())
                .params("patNumber", patNumber)
                .params("identity", identity)
                .params("opdDate", DateUtils.formatDate2(opdDate))
                .params("deptID", deptID)
                .params("opdTimeID", opdTimeID)
                .params("doctorID", doctorID)
                .params("IP", ip)
                .execute(new DialogCallback<LzyResponse<List<CRegister>>>(getActivity()) {

                    @Override
                    public void onSuccess(LzyResponse<List<CRegister>> responseData, Call call, Response response) {
                        Iterator<Vis> stuIter = listVis.iterator();
                        while (stuIter.hasNext()) {
                            Vis v = stuIter.next();
                            if (v.opdDate.equals(opdDate) && v.opdTimeID.equals(opdTimeID) && v.doctorID.equals(doctorID))
                                stuIter.remove();//这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.d(e.getMessage());
                        TUtils.showLong(e.getMessage());
                    }
                });
    }

}
