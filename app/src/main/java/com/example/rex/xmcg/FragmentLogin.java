package com.example.rex.xmcg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private String validateStr, nameStr, phoneStr, identityStr;

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
                validateStr = CommonUtils.getRandomCode();

                break;
            case R.id.login_btn:
                Logger.d("rex");
                nameStr = name.getText().toString();
                phoneStr = phone.getText().toString();
                identityStr = identity.getText().toString();
                //if (!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(phoneStr) && !TextUtils.isEmpty(identityStr)) {
                    RequestParams params = new RequestParams("http://192.168.1.13/test/ckeckLogin.php");
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
                //}
                break;
        }
    }
}
