package com.example.rex.xmcg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class ManagerUserActivity extends AppCompatActivity {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.ll_user_info)
    protected LinearLayout ll_user_info;
    @BindView(R.id.ll_add_user)
    protected LinearLayout ll_add_user;
    @BindView(R.id.delete_user_btn)
    protected Button delete_user_btn;
    @BindView(R.id.next)
    protected Button next;
    @BindView(R.id.name)
    protected TextView name;
    @BindView(R.id.identity)
    protected TextView identity;
    @BindView(R.id.pat_number)
    protected TextView pat_number;
    @BindView(R.id.rv_user)
    protected RecyclerView mRecyclerView;
    private UserAdapter adapter;
    private ArrayList<User> list = new ArrayList<>();

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


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new UserAdapter(this, list);
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
            }
        });

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(adapter);
        GetFamily();
    }

    @OnClick({add_user, R.id.delete_user_btn})
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
                        .params("identity", (String) SPUtils.get(this, "identity", ""))
                        .execute(new DialogCallback<LzyResponse<List<User>>>(this) {

                            @Override
                            public void onSuccess(LzyResponse<List<User>> responseData, Call call, Response response) {
                                ArrayList<User> tmp = (ArrayList<User>) responseData.data;
                                if (tmp.size() > 0) {
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
                break;
            case R.id.next:

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

}
