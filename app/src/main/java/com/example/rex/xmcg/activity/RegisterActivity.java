package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rex.gen.UserGDao;
import com.example.rex.xmcg.Dao.UserG;
import com.example.rex.xmcg.MyApplication;
import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.Doctor;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.model.Register;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.utils.TUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.lzy.okgo.OkGo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.edit_user)
    protected TextView edit_user;
    @BindView(R.id.identity)
    protected TextView identity;
    @BindView(R.id.name)
    protected TextView name;
    @BindView(R.id.pat_number)
    protected TextView pat_number;
    @BindView(R.id.ymd)
    protected TextView ymd;
    @BindView(R.id.department)
    protected TextView department;
    @BindView(R.id.avatar)
    protected RoundedImageView avatar;
    private String opdBeginDate, opdTimeID;
    private Doctor doctor;
    private ArrayList<Register> registerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("挂号信息");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        doctor = (Doctor) bundle.getSerializable("doctor");
        opdBeginDate = doctor.opdDate;
        opdTimeID = doctor.opdTimeID;

        showUserInfo((String) SPUtils.get(this, "identity", "0"), (String) SPUtils.get(this, "patNumber", "0"));
        showRegisterInfo(doctor, opdBeginDate, opdTimeID);
        showAllUsers();
        if (doctor.doctorBean != null) {
            Glide.with(this).load(doctor.doctorBean.image).into(avatar);
        }
    }


    private void showUserInfo(String identityStr, String patNumberStr) {
        identity.setText(identityStr);
        pat_number.setText(patNumberStr);
    }

    private void showRegisterInfo(Doctor doctor, String opdBeginDate, String opdTimeID) {
        name.setText(doctor.doctorName);
        department.setText(doctor.deptName);
        long stamp = DateUtils.formatStamp(opdBeginDate);
        String week = DateUtils.getWeek(stamp);
        String time = "上午";
        if (opdTimeID.equals("2")) {
            time = "下午";
        }
        String date = DateUtils.formatDate(opdBeginDate) + " " + week + " " + time;
        ymd.setText(date);
    }

    private void showAllUsers() {
        UserGDao userDao = MyApplication.getInstances().getDaoSession().getUserGDao();
        List<UserG> users = userDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName() + ",";
        }
    }

    @OnClick(R.id.edit_user)
    protected void editUser(View v) {
        startActivity(new Intent(this, ManagerUserActivity.class));
    }

    @OnClick(R.id.register_ok)
    protected void register_ok(View v) {
        OkGo.post(URL.REGISTER)
                .tag(this)
                .params("identity", (String) SPUtils.get(this, "identity", ""))
                .params("opdDate", opdBeginDate)
                .params("deptID", doctor.deptID)
                .params("opdTimeID", opdTimeID)
                .params("doctorID", doctor.doctorID)
                .params("IP", CommonUtils.getIPAddress(this))
                .execute(new DialogCallback<LzyResponse<List<Register>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<Register>> responseData, Call call, Response response) {
                        registerList = (ArrayList<Register>) responseData.data;
                        if (registerList.size() > 0) {
                            Register register = registerList.get(0);
                            Intent mIntent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("register", register);
                            mBundle.putString("opdDate", opdBeginDate);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            finish();
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
