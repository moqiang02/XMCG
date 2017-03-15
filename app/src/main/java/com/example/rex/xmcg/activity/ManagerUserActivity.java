package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.UserAdapter;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.model.User;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.utils.TUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.rex.xmcg.R.id.add_user;
import static com.lzy.okgo.OkGo.getContext;


public class ManagerUserActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.ll_user_info)
    protected LinearLayout ll_user_info;
    @BindView(R.id.ll_add_user)
    protected LinearLayout ll_add_user;
    @BindView(R.id.delete_user_btn)
    protected Button delete_user_btn;
    @BindView(R.id.validate_btn)
    protected Button validate_btn;
    @BindView(R.id.next)
    protected Button next;
    @BindView(R.id.name)
    protected TextView name;
    @BindView(R.id.add_name)
    protected TextView add_name;
    @BindView(R.id.add_number)
    protected TextView add_number;
    @BindView(R.id.add_phone)
    protected TextView add_phone;
    @BindView(R.id.add_validate)
    protected TextView add_validate;
    @BindView(R.id.identity)
    protected TextView identity;
    @BindView(R.id.pat_number)
    protected TextView pat_number;
    @BindView(R.id.rv_user)
    protected RecyclerView mRecyclerView;
    private UserAdapter adapter;
    private ArrayList<User> list = new ArrayList<>();
    private String phoneStr, randomCode, fid;

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


        mRecyclerView.setLayoutManager(new GridLayoutManager(ManagerUserActivity.this, 4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new UserAdapter(ManagerUserActivity.this, list);
        adapter.setOnItemClickListener(new UserAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, User u) {
                adapter.checkCurrent(u);
                ll_user_info.setVisibility(View.VISIBLE);
                ll_add_user.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                delete_user_btn.setVisibility(View.VISIBLE);
                name.setText(u.name);
                identity.setText(u.cardNo);
                pat_number.setText(u.medicalNo);
                fid = u.fid;
            }
        });

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(adapter);
        GetFamily();
    }

    @OnClick({R.id.add_user, R.id.delete_user_btn, R.id.validate_btn, R.id.next})
    void butterknifeOnItemClick(View view) {
        switch (view.getId()) {
            case add_user:
                ll_user_info.setVisibility(View.GONE);
                ll_add_user.setVisibility(View.VISIBLE);
                delete_user_btn.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);

                break;
            case R.id.delete_user_btn:
                OkGo.post(URL.DEL_USER)
                        .tag(this)
                        .params("userid", "1870")
                        .params("fid", fid)
                        .execute(new DialogCallback<LzyResponse<User>>(this) {

                            @Override
                            public void onSuccess(LzyResponse<User> responseData, Call call, Response response) {
                                GetFamily();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                TUtils.showLong(e.getMessage());
                            }
                        });
                break;
            case R.id.next:
                String nameStr = add_name.getText().toString();
                String numberStr = add_number.getText().toString();
                String validateStr = add_validate.getText().toString();

                if (TextUtils.isEmpty(nameStr)) {
                    TUtils.showLong("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(numberStr)) {
                    TUtils.showLong("身份证号或病历号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phoneStr)) {
                    TUtils.showLong("手机不能为空");
                    return;
                }
/*                if (TextUtils.isEmpty(validateStr)) {
                    TUtils.showLong("验证码不能为空");
                    return;
                }
                if (!validateStr.equals(SPUtils.get(getContext(),"randomCode","dfs1"))) {
                    TUtils.showLong( "验证码错误");
                    return;
                }*/

                OkGo.post(URL.ADD_USER)
                        .tag(this)
                        .params("identity", numberStr)
                        .params("name", nameStr)
                        .params("phone", phoneStr)
                        .params("userid", "1870")
                        .execute(new DialogCallback<LzyResponse<User>>(this) {

                            @Override
                            public void onSuccess(LzyResponse<User> responseData, Call call, Response response) {
                                GetFamily();


//                                LinearLayout ll = (LinearLayout) mRecyclerView.getChildAt(0);
//                                ll.performClick();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                TUtils.showLong(e.getMessage());
                            }
                        });
                break;
            case R.id.validate_btn:
                phoneStr = add_phone.getText().toString();
                randomCode = CommonUtils.getRandomCode(6);
                TimeCount time = new TimeCount(60000, 1000);
/*                OkGo.post(URL.VALIDATE)
                        .tag(getActivity())
                        .params("randomCode",randomCode)
                        .params("mobile",phoneStr.trim())
                        .execute(new DialogCallback<LzyResponse<String>>(getActivity()) {

                            @Override
                            public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                                SPUtils.put(getContext(),"randomCode",randomCode);
                                time.start();// 开始计时
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                TUtils.showLong(e.getMessage());
                                SPUtils.put(getContext(),"randomCode","qas");
                                validate_btn.setText("请求验证");
                                validate_btn.setClickable(true);
                            }
                        });*/
                break;
        }
    }

    private void GetFamily() {
        OkGo.post(URL.GET_Family)
                .tag(this)
                .params("identity", (String) SPUtils.get(this, "identity", ""))
                .execute(new DialogCallback<LzyResponse<List<User>>>(this) {

                    @Override
                    public void onSuccess(LzyResponse<List<User>> responseData, Call call, Response response) {
                        ArrayList<User> tmp = (ArrayList<User>) responseData.data;
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

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            SPUtils.put(getContext(), "randomCode", "qas1");
            validate_btn.setText("请求验证");
            validate_btn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            validate_btn.setClickable(false);//防止重复点击
            validate_btn.setText(millisUntilFinished / 1000 + "秒");
        }
    }

}
