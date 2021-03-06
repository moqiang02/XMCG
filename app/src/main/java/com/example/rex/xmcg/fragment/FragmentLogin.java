package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rex.gen.UserGDao;
import com.example.rex.xmcg.Dao.UserG;
import com.example.rex.xmcg.MyApplication;
import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.callback.DialogCallback;
import com.example.rex.xmcg.model.EventType;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.model.User;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.SPUtils;
import com.example.rex.xmcg.utils.TUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentLogin extends android.support.v4.app.Fragment implements View.OnClickListener {
    private EditText name, identity, phone, validate;
    private Button validate_btn, login_btn;
    private String validateStr, nameStr, phoneStr, identityStr, randomCode;
    private TimeCount time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        name = (EditText) view.findViewById(R.id.name);
        identity = (EditText) view.findViewById(R.id.identity);
        phone = (EditText) view.findViewById(R.id.phone);
        validate = (EditText) view.findViewById(R.id.validate);
        validate_btn = (Button) view.findViewById(R.id.validate_btn);
        login_btn = (Button) view.findViewById(R.id.login_btn);
        validate_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validate_btn:
                phoneStr = phone.getText().toString();
                randomCode = CommonUtils.getRandomCode(6);
                time = new TimeCount(60000, 1000);
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
            case R.id.login_btn:
                nameStr = name.getText().toString();
                identityStr = identity.getText().toString();
                validateStr = validate.getText().toString();

                if (TextUtils.isEmpty(nameStr)) {
                    TUtils.showLong( "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(identityStr)) {
                    TUtils.showLong( "身份证号不能为空");
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
                if (!validateStr.equals(SPUtils.get(getContext(),"randomCode","dfs"))) {
                    TUtils.showLong( "验证码错误");
                    return;
                }*/

                OkGo.post(URL.CHECK_LOGIN)
                        .tag(getActivity())
                        .params("identity",identityStr)//350211198302083056
                        .execute(new DialogCallback<LzyResponse<User>>(getActivity()) {

                            @Override
                            public void onSuccess(LzyResponse<User> responseData, Call call, Response response) {
                                Logger.d(responseData);
                                User user = (User)responseData.data;
                                SPUtils.put(getContext(),"patNumber",user.patNumber);
                                SPUtils.put(getContext(),"isFirst",user.isFirst);
                                SPUtils.put(getContext(),"identity",identityStr);
                                SPUtils.put(getContext(),"name",nameStr);
                                SPUtils.put(getContext(),"isLogin",true);
                                String userName;
                                if (user.isFirst.equals("Y")){
                                    userName = nameStr;
                                }else{
                                    userName = user.name;
                                }
                                UserG mUser = new UserG(null,userName,phoneStr,identityStr,user.patNumber,user.idType,
                                        user.idNumber,user.isFirst,user.memo);
                                UserGDao userDao = MyApplication.getInstances().getDaoSession().getUserGDao();
                                userDao.insert(mUser);//添加一个

                                EventBus.getDefault().post(new EventType.Frag());
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                TUtils.showLong(e.getMessage());
                            }
                        });

                break;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            SPUtils.put(getContext(),"randomCode","qas");
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
