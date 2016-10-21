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

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.RegisterAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.fragment.CalendarDialogFragment;
import com.example.rex.xmcg.model.Doctor;
import com.example.rex.xmcg.model.EventType;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.ViewFindUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterListActivity extends AppCompatActivity {
    private String deptID, deptName;
    private ArrayList<Doctor> doctorList;
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
    private List<Map<String, Object>> dateList;
    private SimpleAdapter simple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

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
                if (position == 1) {
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
//        loadData(deptID, currDate, currDate, "1");

        long cuttStamp = System.currentTimeMillis();
        dateList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("date", DateUtils.getMonDay(cuttStamp));
            item.put("week", DateUtils.getWeek(cuttStamp));
            dateList.add(item);
            cuttStamp += 24 * 3600 * 1000;
        }

        simple = new SimpleAdapter(this, dateList, R.layout.item_grid_date,
                new String[]{"date", "week"}, new int[]{R.id.tv_date, R.id.tv_week});
        gridView.setAdapter(simple);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
                opdBeginDate = opdEndDate = DateUtils.getYmdStr(System.currentTimeMillis() + 24 * 3600 * 1000 * postion);
                loadData(deptID, opdBeginDate, opdBeginDate, "1");
                gridView.getChildAt(postion).setSelected(true);
                tabLayout_1.setCurrentTab(0);
            }
        });
        gridView.post(new Runnable() {
            @Override
            public void run() {
                gridView.performItemClick(gridView.getChildAt(0), 0, gridView.getItemIdAtPosition(0));
            }

        });
    }

    private void loadData(String deptID, final String opdBeginDate, String opdEndDate, final String opdTimeID) {

        OkGo.post(URL.GET_REGISTER_LIST)
                .tag(this)
                .params("opdBeginDate", opdBeginDate)
                .params("opdEndDate", opdEndDate)
                .params("doctorID", "")
                .params("deptID", deptID)
                .params("opdTimeID", opdTimeID)
                .execute(new DialogCallback<LzyResponse<List<Doctor>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<Doctor>> responseData, Call call, Response response) {
                        doctorList = (ArrayList<Doctor>) responseData.data;
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(RegisterListActivity.this));
                        // 设置ItemAnimator
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        // 设置固定大小
                        mRecyclerView.setHasFixedSize(true);
                        // 初始化自定义的适配器
                        adapter = new RegisterAdapter(RegisterListActivity.this, doctorList);
                        adapter.setOnItemClickListener(new RegisterAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Doctor doctor) {
//                            Logger.d(doctor.doctorName);
                                if (!doctor.isFull.equals("Y") && doctor.canReg.equals("Y")) {
                                    Intent mIntent = new Intent(RegisterListActivity.this, RegisterActivity.class);
                                    Bundle mBundle = new Bundle();
                                    mBundle.putSerializable("doctor", doctor);
                                    mBundle.putString("opdBeginDate", opdBeginDate);
                                    mBundle.putString("opdTimeID", opdTimeID);
                                    mIntent.putExtras(mBundle);
                                    startActivity(mIntent);
                                }
                            }
                        });
                        // 为mRecyclerView设置适配器
                        mRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @OnClick(R.id.go_date)
    void goDate(View view) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear, curr_month, next_month, start_day, end_day;
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        curr_month = c.get(Calendar.MONTH);// 获取当前月份
        start_day = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        c.setTimeInMillis(System.currentTimeMillis() + 24 * 3600 * 1000 * 14);//14天后
        next_month = c.get(Calendar.MONTH);
        end_day = c.get(Calendar.DAY_OF_MONTH);
        new CalendarDialogFragment(curr_month, next_month, start_day, end_day).show(getSupportFragmentManager(), "test-calendar");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIThread(EventType.CalendarEvent event) {
        Date date = event.date;
        long times = date.getTime();
        dateList.clear();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("date", DateUtils.getMonDay(times));
            item.put("week", DateUtils.getWeek(times));
            dateList.add(item);
            times += 24 * 3600 * 1000;
        }
        simple.notifyDataSetChanged();
        opdBeginDate = opdEndDate = DateUtils.getYmdStr(date.getTime());
        loadData(deptID, opdBeginDate, opdEndDate, "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}