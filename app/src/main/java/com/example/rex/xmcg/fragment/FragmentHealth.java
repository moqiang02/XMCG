package com.example.rex.xmcg.fragment;

import android.content.Intent;
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
import com.example.rex.xmcg.activity.KnowledgeDetailActivity;
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
        adapter.setOnItemClickListener(new KnowledgeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Knowledge knowledge) {

                Intent mIntent = new Intent(getActivity(), KnowledgeDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("knowledge", knowledge);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);

            }
        });

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(adapter);
    }

    private void getList() {
        OkGo.post(URL.GET_KNOWLEDGE_LIST)
                .tag(getActivity())
                .execute(new JsonCallback<LzyResponse<List<Knowledge>>>() {

                    @Override
                    public void onSuccess(LzyResponse<List<Knowledge>> responseData, Call call, Response response) {
                        ArrayList<Knowledge> datas = (ArrayList<Knowledge>) responseData.data;
                        if (datas.size() > 0) {
                            list.addAll(datas);
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
