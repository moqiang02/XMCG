package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.weiget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerUser extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.user_info_ll)
    protected LinearLayout user_info_ll;
    @BindView(R.id.add_user_ll)
    protected LinearLayout add_user_ll;
    @BindView(R.id.delete_user_btn)
    protected Button delete_user_btn;
    @BindView(R.id.add_user_btn)
    protected Button add_user_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);
        ButterKnife.bind(this);
        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle("就诊人管理");
    }

    @OnClick({R.id.add_user, R.id.delete_user_btn, R.id.add_user_btn})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                user_info_ll.setVisibility(View.GONE);
                add_user_ll.setVisibility(View.VISIBLE);
                delete_user_btn.setVisibility(View.GONE);
                add_user_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.delete_user_btn:
                Log.e("hm", "tv_test3");
                break;
            case R.id.add_user_btn:
                Log.e("hm", "tv_test3");
                break;
        }
    }
}
