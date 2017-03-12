package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.DepartmentListAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.DepartmentList;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.utils.TUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DoctorInfoActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    protected RecyclerView mRecyclerView;
    private DepartmentListAdapter adapter;
    private ArrayList<DepartmentList> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new DepartmentListAdapter(this, list);
        mRecyclerView.setAdapter(adapter);
        getDepartmentList();
    }

    private void getDepartmentList(){
        OkGo.post(URL.GET_DEPARTMENT_LIST)
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<DepartmentList>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<DepartmentList>> responseData, Call call, Response response) {
                        ArrayList<DepartmentList> tmp = (ArrayList<DepartmentList>) responseData.data;
                        if (tmp.size() > 0) {
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
