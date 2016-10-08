package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.utils.CommonUtils;
import com.example.rex.xmcg.utils.T;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


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
                    T.showLong(getContext(), "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(identityStr)) {
                    T.showLong(getContext(), "身份证号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phoneStr)) {
                    T.showLong(getContext(), "手机不能为空");
                    return;
                }
                if (TextUtils.isEmpty(nameStr)) {
                    T.showLong(getContext(), "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(validateStr)) {
                    T.showLong(getContext(), "验证码不能为空");
                    return;
                }
                if (!randomCode.equals(validateStr)) {
                    T.showLong(getContext(), "验证码错误");
                    return;
                }

                RequestParams params = new RequestParams(URL.CHECK_LOGIN);
                params.addQueryStringParameter("identity", "350211198302083056");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
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

                break;
        }
    }
}
