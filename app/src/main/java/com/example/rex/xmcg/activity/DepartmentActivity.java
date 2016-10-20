package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.DeptGroupAdapter;
import com.example.rex.xmcg.model.DepartmentGroup;
import com.example.rex.xmcg.model.ResultBean;
import com.example.rex.xmcg.weiget.LoadingDialog;
import com.example.rex.xmcg.weiget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.group)
    protected ListView group;
    @BindView(R.id.son)
    protected ListView son;
    private Gson gson = new Gson();
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
//        final LoadingDialog loadingDialog = new LoadingDialog(this);
//        loadingDialog.show();
        RequestParams params = new RequestParams(URL.GET_DEPT_GROUP);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                loadingDialog.dismiss();
                java.lang.reflect.Type type = new TypeToken<ResultBean<DepartmentGroup>>() {
                }.getType();
                ResultBean bean = gson.fromJson(result, type);
                groups = (ArrayList<DepartmentGroup>) bean.dataList;
                Logger.d(result + "-----------");
                if (bean.code == 200) {
                    adapter = new DeptGroupAdapter(DepartmentActivity.this, groups);
                    group.setAdapter(adapter);
                    onClickGroupItem();
                    group.performItemClick(group.getChildAt(0), 0, group.getItemIdAtPosition(0));
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

    private void onClickGroupItem() {
        group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                final LoadingDialog loadingDialog = new LoadingDialog(DepartmentActivity.this);
                loadingDialog.show();
                RequestParams params = new RequestParams(URL.GET_DEPT);
                params.addQueryStringParameter("DeptGroupId", groups.get(position).deptGroupID);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        loadingDialog.dismiss();
                        java.lang.reflect.Type type = new TypeToken<ResultBean<DepartmentGroup>>() {
                        }.getType();
                        ResultBean bean = gson.fromJson(result, type);
                        departments = (ArrayList<DepartmentGroup>) bean.dataList;
                        ArrayList<String> deptTexts = new ArrayList<String>();
                        for (DepartmentGroup item : departments) {
                            deptTexts.add(item.deptName);
                        }
                        if (bean.code == 200) {
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
        });
    }
}
