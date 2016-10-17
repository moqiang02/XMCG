package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.RegisterAdapter;
import com.example.rex.xmcg.model.Register;
import com.example.rex.xmcg.model.ResultBean;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.weiget.LoadingDialog;
import com.example.rex.xmcg.weiget.TitleBar;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterListActivity extends AppCompatActivity {
    private String deptID, deptName;
    private Gson gson = new Gson();
    private ArrayList<Register> registerList;
    private RegisterAdapter adapter;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.gv_date)
    protected GridView gridView;
    @BindView(R.id.date_tips)
    protected TextView date_tips;
    private String[] mTitles = {"上午", "下午"};
    private View mDecorView;
    private String opdBeginDate, opdEndDate, opdTimeID;
    private SegmentTabLayout tabLayout_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        ButterKnife.bind(this);

        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        date_tips.setText(DateUtils.getYmdwStr());

        mDecorView = getWindow().getDecorView();
        tabLayout_1 = ViewFindUtils.find(mDecorView, R.id.tl_1);
        tabLayout_1.setTabData(mTitles);
        tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    loadData(deptID, opdBeginDate, opdBeginDate, "1");
                }else{
                    loadData(deptID, opdBeginDate, opdBeginDate, "2");
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        Intent intent = getIntent();
        deptID = intent.getStringExtra("deptID");
        deptName = intent.getStringExtra("deptName");
        titleBar.setTitle(deptName);
        String currDate = DateUtils.getYmdStr(System.currentTimeMillis());
        loadData(deptID, currDate, currDate, "1");

        long cuttStamp = System.currentTimeMillis();
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("date", DateUtils.getMonDay(cuttStamp));
            item.put("week", DateUtils.getWeek(cuttStamp));
            list.add(item);
            cuttStamp += 24 * 3600 * 1000;
        }

        SimpleAdapter simple = new SimpleAdapter(this, list, R.layout.item_grid_date,
                new String[]{"date", "week"}, new int[]{R.id.tv_date, R.id.tv_week});
        gridView.setAdapter(simple);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
                if (postion==0){
                    gridView.getChildAt(0).setBackgroundResource(R.color.blue);
                }else {
                    gridView.getChildAt(0).setBackgroundResource(R.color.white);
                }
                gridView.getChildAt(postion).setSelected(true);

                tabLayout_1.setCurrentTab(0);
                opdBeginDate = opdEndDate = DateUtils.getYmdStr(System.currentTimeMillis() + 24 * 3600 * 1000 * postion);
                loadData(deptID, opdBeginDate, opdBeginDate, "1");
            }
        });
        gridView.post(new Runnable() {
            @Override
            public void run() {
                gridView.performItemClick(gridView.getChildAt(0), 0, gridView.getItemIdAtPosition(0));
            }

        });
    }

    private void loadData(String deptID, String opdBeginDate, String opdEndDate, String opdTimeID) {

        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        RequestParams params = new RequestParams(URL.GET_REGISTER_LIST);
        params.addQueryStringParameter("opdBeginDate", opdBeginDate);
        params.addQueryStringParameter("opdEndDate", opdEndDate);
        params.addQueryStringParameter("doctorID", "");
        params.addQueryStringParameter("deptID", deptID);
        params.addQueryStringParameter("opdTimeID", opdTimeID);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                loadingDialog.dismiss();
                java.lang.reflect.Type type = new TypeToken<ResultBean<Register>>() {
                }.getType();
                ResultBean bean = gson.fromJson(result, type);
                Logger.json(result);
                registerList = (ArrayList<Register>) bean.dataList;

                if (bean.code == 200) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(x.app()));
                    // 设置ItemAnimator
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    // 设置固定大小
                    mRecyclerView.setHasFixedSize(true);
                    // 初始化自定义的适配器
                    adapter = new RegisterAdapter(x.app(), registerList);
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