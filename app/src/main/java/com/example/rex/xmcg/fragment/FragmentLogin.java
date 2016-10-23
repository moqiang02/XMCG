package com.example.rex.xmcg.fragment;

import android.os.Bundle;
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
                randomCode = CommonUtils.getRandomCode();
                Logger.d(randomCode);
                break;
            case R.id.login_btn:
                nameStr = name.getText().toString();
                phoneStr = phone.getText().toString();
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
//                if (TextUtils.isEmpty(validateStr)) {
//                    TUtils.showLong("验证码不能为空");
//                    return;
//                }
//                if (!randomCode.equals(validateStr)) {
//                    TUtils.showLong( "验证码错误");
//                    return;
//                }

                OkGo.post(URL.CHECK_LOGIN)
                        .tag(getActivity())
                        .params("identity",identityStr)//350211198302083056
                        .execute(new DialogCallback<LzyResponse<User>>(getActivity()) {

                            @Override
                            public void onSuccess(LzyResponse<User> responseData, Call call, Response response) {
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
                            }
                        });

                break;
        }
    }
}
