package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.RegisterAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.Doctor;
import com.example.rex.xmcg.model.EventType;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.example.rex.xmcg.weiget.calendar.EventDecorator;
import com.example.rex.xmcg.weiget.calendar.MySelectorDecorator;
import com.example.rex.xmcg.weiget.calendar.OneDayDecorator;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterListActivity extends AppCompatActivity implements OnDateSelectedListener {
    private String deptID, deptName;
    private ArrayList<Doctor> doctorList = new ArrayList<>();
    private RegisterAdapter adapter;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    private View mDecorView;
    private String opdBeginDate, opdEndDate;
    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();


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

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear, curr_month, next_month, start_day, end_day;
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        curr_month = c.get(Calendar.MONTH);// 获取当前月份
        start_day = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        c.setTimeInMillis(System.currentTimeMillis() + 24 * 3600 * 1000 * 14);//14天后
        next_month = c.get(Calendar.MONTH);
        end_day = c.get(Calendar.DAY_OF_MONTH);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), curr_month, start_day);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), next_month, end_day);

        widget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        widget.addDecorators(
                new MySelectorDecorator(this),
                //new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
//        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        getRegisterStatus();

        Intent intent = getIntent();
        deptID = intent.getStringExtra("deptID");
        deptName = intent.getStringExtra("deptName");
        titleBar.setTitle(deptName);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(RegisterListActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new RegisterAdapter(RegisterListActivity.this, doctorList);
        adapter.setOnItemClickListener(new RegisterAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Doctor doctor) {
                if ((Boolean) SPUtils.get(RegisterListActivity.this, "isLogin", false)) {

                    if (!doctor.isFull.equals("Y") && doctor.canReg.equals("Y")) {
                        Intent mIntent = new Intent(RegisterListActivity.this, RegisterActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("doctor", doctor);
                        mBundle.putString("opdBeginDate", opdBeginDate);
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                    }
                } else {
                    AlertView alertView = new AlertView("提示", "请先登陆", "确定", null, null,
                            RegisterListActivity.this, AlertView.Style.Alert,
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    startActivity(new Intent(RegisterListActivity.this, MainActivity.class));
                                    EventBus.getDefault().post(new EventType.ToLogin());
                                }
                            });
                    alertView.show();
                }
            }
        });

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(adapter);

        String currDate = DateUtils.getYmdStr(System.currentTimeMillis());
        loadData(deptID, currDate, currDate);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
        Logger.d(date.getCalendar());
        Calendar calendar = date.getCalendar();
        opdBeginDate = opdEndDate = DateUtils.formatStrFromCalendar(calendar);
        loadData(deptID, opdBeginDate, opdEndDate);
    }

    private void loadData(String deptID, String opdBeginDate, String opdEndDate) {

        OkGo.post(URL.GET_REGISTER_LIST)
                .tag(this)
                .params("opdBeginDate", opdBeginDate)
                .params("opdEndDate", opdEndDate)
                .params("doctorID", "")
                .params("deptID", deptID)
                .execute(new DialogCallback<LzyResponse<List<Doctor>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<Doctor>> responseData, Call call, Response response) {
                        doctorList.addAll(responseData.data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void getRegisterStatus() {
        String opdBegin = DateUtils.getYmdStr(System.currentTimeMillis());
        String opdEnd = DateUtils.getYmdStr(System.currentTimeMillis()+ 24 * 3600 * 1000 * 14);
        OkGo.post(URL.GET_REGISTER_STAUS)
                .tag(this)
                .params("opdBeginDate", opdBegin)
                .params("opdEndDate", opdEnd)
                .params("doctorID", "")
                .params("deptID", deptID)
                .execute(new DialogCallback<LzyResponse<List<Doctor>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<Doctor>> responseData, Call call, Response response) {
                        doctorList.addAll(responseData.data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            widget.addDecorator(new EventDecorator(RegisterListActivity.this, Color.RED, calendarDays));
        }
    }

}