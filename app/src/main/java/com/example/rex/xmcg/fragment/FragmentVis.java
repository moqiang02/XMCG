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
import com.example.rex.xmcg.adapter.VisAdapter;
import com.example.rex.xmcg.model.CRegister;
import com.example.rex.xmcg.model.ResultBean;
import com.example.rex.xmcg.model.Vis;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.weiget.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/10.
 */
public class FragmentVis extends android.support.v4.app.Fragment {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private VisAdapter adapter;
    private Gson gson = new Gson();
    private ArrayList<Vis> listVis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vis, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        final LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        RequestParams params = new RequestParams(URL.GET_RECORD);
        params.addQueryStringParameter("isFirst", (String) SPUtils.get(getContext(), "isFirst", ""));
        params.addQueryStringParameter("patNumber", (String) SPUtils.get(getContext(), "patNumber", ""));
        params.addQueryStringParameter("identity", (String) SPUtils.get(getContext(), "identity", ""));
        params.addQueryStringParameter("type", "new");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                loadingDialog.dismiss();
                Logger.json(result);
                java.lang.reflect.Type type = new TypeToken<ResultBean<Vis>>() {
                }.getType();
                ResultBean bean = gson.fromJson(result, type);
                listVis = (ArrayList<Vis>) bean.dataList;

                if (bean.code == 200) {
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
                                    vis.opdDate, vis.deptID, vis.opdTimeID,vis.doctorID, CommonUtils.getIPAddress(getContext()));
                        }

                    });
                    // 为mRecyclerView设置适配器
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadingDialog.dismiss();
                Logger.e(ex.getMessage());
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                loadingDialog.dismiss();
                Logger.e("cancelled");
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                Logger.e("onFinished");
                loadingDialog.dismiss();

            }
        });


    }


    private void cancelRegister(String patNumber, String identity, final String opdDate, final String deptID, final String opdTimeID, final String doctorID, String ip) {
        final LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        RequestParams params = new RequestParams(URL.CANCEL_REGISTER);
        params.addQueryStringParameter("patNumber", patNumber);
        params.addQueryStringParameter("identity", identity);
        params.addQueryStringParameter("opdDate", DateUtils.formatDate2(opdDate));
        params.addQueryStringParameter("deptID", deptID);
        params.addQueryStringParameter("opdTimeID", opdTimeID);
        params.addQueryStringParameter("doctorID", doctorID);
        params.addQueryStringParameter("IP", ip);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                loadingDialog.dismiss();
                Logger.json(result);
                java.lang.reflect.Type type = new TypeToken<ResultBean<CRegister>>() {
                }.getType();
                ResultBean bean = gson.fromJson(result, type);

                if (bean.code == 200) {
                    Iterator<Vis> stuIter = listVis.iterator();
                    while (stuIter.hasNext()) {
                        Vis v = stuIter.next();
                        if (v.opdDate.equals(opdDate) && v.opdTimeID.equals(opdTimeID) && v.doctorID.equals(doctorID))
                            stuIter.remove();//这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadingDialog.dismiss();
                Logger.e(ex.getMessage());
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                loadingDialog.dismiss();
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                loadingDialog.dismiss();

            }
        });
    }
}
