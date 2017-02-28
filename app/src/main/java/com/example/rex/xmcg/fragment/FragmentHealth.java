package com.example.rex.xmcg.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.URL;
import com.example.rex.xmcg.adapter.KnowledgeAdapter;
import com.example.rex.xmcg.callback.JsonCallback;
import com.example.rex.xmcg.model.Knowledge;
import com.example.rex.xmcg.model.LzyResponse;
import com.example.rex.xmcg.utils.TUtils;
import com.example.rex.xmcg.weiget.TitleBar;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Rex on 2016/10/7.
 */
public class FragmentHealth extends android.support.v4.app.Fragment {
    @BindView(R.id.title_bar)
    protected TitleBar titleBar;
    @BindView(R.id.knowledge_rv)
    protected RecyclerView mRecyclerView;
    private KnowledgeAdapter adapter;
    private ArrayList<Knowledge> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        ButterKnife.bind(this, view);
        titleBar.setLeftImageResource(R.mipmap.icon_logo);
        titleBar.setTitle("健康");
        titleBar.addAction(new TitleBar.ImageAction(R.mipmap.message) {
            @Override
            public void performAction(View view) {
                Toast.makeText(getActivity(), "点击了收藏", Toast.LENGTH_SHORT).show();
            }
        });
        initView();
        getList();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new KnowledgeAdapter(getActivity(), list);
/*        adapter.setOnItemClickListener(new RegisterAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Doctor doctor) {
                if ((Boolean) SPUtils.get(getActivity(), "isLogin", false)) {

                    if (!doctor.isFull.equals("Y") && doctor.canReg.equals("Y")) {
                        Intent mIntent = new Intent(getActivity(), RegisterActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("doctor", doctor);
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                    }
                } else {
                    AlertView alertView = new AlertView("提示", "请先登陆", "确定", null, null,
                            getActivity(), AlertView.Style.Alert,
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    EventBus.getDefault().post(new EventType.ToLogin());
                                }
                            });
                    alertView.show();
                }
            }
        });*/

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(adapter);
    }

    private void getList() {
        OkGo.post(URL.GET_KNOWLEDGE_LIST)
                .tag(getActivity())
                .execute(new JsonCallback<LzyResponse<List<Knowledge>>>() {

                    @Override
                    public void onSuccess(LzyResponse<List<Knowledge>> responseData, Call call, Response response) {
                        list = (ArrayList<Knowledge>) responseData.data;
                        if (list.size()>0) {
                            Knowledge k = list.get(0);
//                            Intent mIntent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
//                            Bundle mBundle = new Bundle();
//                            mBundle.putSerializable("register", register);
//                            mBundle.putString("opdDate", opdBeginDate);
//                            mIntent.putExtras(mBundle);
//                            startActivity(mIntent);
//                            getActivity().finish();
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
