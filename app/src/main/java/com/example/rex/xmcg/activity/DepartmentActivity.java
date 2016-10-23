package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.DeptGroupAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.DepartmentGroup;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.weiget.TitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DepartmentActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.group)
    protected ListView group;
    @BindView(R.id.son)
    protected ListView son;
    private DeptGroupAdapter adapter;
    private ArrayList<DepartmentGroup> groups;
    private ArrayList<DepartmentGroup> departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        ButterKnife.bind(this);

        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("选择科室");

        initData();
    }

    private void initData() {

        OkGo.get(URL.GET_DEPT_GROUP)
                .tag(this)
                .cacheKey("DeptGroup")       //必须保证key唯一,否则数据会发生覆盖
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheTime(10 * 10 * 1000)
                .execute(new DialogCallback<LzyResponse<ArrayList<DepartmentGroup>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<ArrayList<DepartmentGroup>> responseData, Call call, Response response) {
                        groups = responseData.data;
                        Logger.d(groups.get(2).deptGroupName);
                        adapter = new DeptGroupAdapter(DepartmentActivity.this, groups);
                        group.setAdapter(adapter);
                        onClickGroupItem();
                        group.performItemClick(group.getChildAt(0), 0, group.getItemIdAtPosition(0));
                    }

                    @Override
                    public void onCacheSuccess(LzyResponse<ArrayList<DepartmentGroup>> responseData, Call call) {
                        onSuccess(responseData, call, null);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void onClickGroupItem() {
        group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                OkGo.post(URL.GET_DEPT)
                        .tag(DepartmentActivity.this)
                        .cacheKey("Dept"+position)       //必须保证key唯一,否则数据会发生覆盖
                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .cacheTime(10 * 10 * 1000)
                        .params("DeptGroupId", groups.get(position).deptGroupID)
                        .execute(new DialogCallback<LzyResponse<ArrayList<DepartmentGroup>>>(DepartmentActivity.this) {

                            @Override
                            public void onSuccess(LzyResponse<ArrayList<DepartmentGroup>> responseData, Call call, Response response) {
                                departments = responseData.data;
                                ArrayList<String> deptTexts = new ArrayList<String>();
                                for (DepartmentGroup item : departments) {
                                    deptTexts.add(item.deptName);
                                }
                                ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(
                                        DepartmentActivity.this,
                                        R.layout.item_dept,
                                        R.id.tv,
                                        deptTexts);
                                son.setAdapter(arrAdapter);
                                son.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.putExtra("deptID", departments.get(position).deptID);
                                        intent.putExtra("deptName", departments.get(position).deptName);
                                        intent.setClass(DepartmentActivity.this, RegisterListActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onCacheSuccess(LzyResponse<ArrayList<DepartmentGroup>> responseData, Call call) {
                                onSuccess(responseData, call, null);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
}
