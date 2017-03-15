package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.DoctorAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.DepartmentBean;
import com.example.rex.xmcg.model.DoctorInfo;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.utils.TUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DoctorListActivity extends AppCompatActivity {
    private DepartmentBean departmentBean;
    private ArrayList<DoctorInfo> list = new ArrayList<>();
    private DoctorAdapter adapter;
    @BindView(R.id.rv)
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        departmentBean = (DepartmentBean) bundle.getSerializable("DepartmentBean");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new DoctorAdapter(this, list);
        adapter.setOnItemClickListener(new DoctorAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, DoctorInfo u) {

            }
        });
        mRecyclerView.setAdapter(adapter);

        getDoctorList(departmentBean.catid);
    }

    private void getDoctorList(String catid) {
        OkGo.post(URL.GET_DOCTOR_LIST)
                .tag(this)
                .params("deptid", catid)
                .execute(new DialogCallback<LzyResponse<List<DoctorInfo>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<DoctorInfo>> responseData, Call call, Response response) {
                        ArrayList<DoctorInfo> tmp = (ArrayList<DoctorInfo>) responseData.data;
                        if (tmp.size() > 0) {
                            list.clear();
                            list.addAll(tmp);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        TUtils.showLong(e.getMessage());
                    }
                });
    }
}
