package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Register;
import com.example.rex.xmcg.utils.DateUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.weiget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterSuccessActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.identity)
    protected TextView identity;
    @BindView(R.id.name)
    protected TextView name;
    @BindView(R.id.username)
    protected TextView username;
    @BindView(R.id.pat_number)
    protected TextView pat_number;
    @BindView(R.id.department)
    protected TextView department;
    @BindView(R.id.ymd)
    protected TextView ymd;
    @BindView(R.id.address)
    protected TextView address;
    private Register register;
    private String opdDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
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
        register = (Register) bundle.getSerializable("register");
        opdDate = bundle.getString("opdDate");
        showRegisterInfo();
        showUserInfo(register.name, (String) SPUtils.get(this, "identity", "0"), (String) SPUtils.get(this, "patNumber", "0"));
    }

    private void showRegisterInfo() {
        name.setText(register.doctorName);
        department.setText(register.deptName);
        long stamp = DateUtils.formatStamp(opdDate);
        String week = DateUtils.getWeek(stamp);
        String time = "上午";
        if (register.opdTimeID.equals("2")) {
            time = "下午";
        }
        String date = DateUtils.formatDate(opdDate) + " " + week + " " + time;
        ymd.setText(date);
        address.setText(register.roomLocation);
    }

    private void showUserInfo(String name, String identityStr, String patNumberStr) {
        username.setText(name);
        identity.setText(identityStr);
        pat_number.setText(patNumberStr);
    }
}
