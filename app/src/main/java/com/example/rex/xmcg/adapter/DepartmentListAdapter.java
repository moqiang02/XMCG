package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.activity.DoctorListActivity;
import com.example.rex.xmcg.model.DepartmentBean;
import com.example.rex.xmcg.model.DepartmentList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Rex on 2016/10/11.
 */

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.ViewHolder> {

    private List<DepartmentList> list;
    private Context mContext;
    private DepartmentAdapter adapter;

    public DepartmentListAdapter(Context context, List<DepartmentList> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_department_list, viewGroup, false);//将创建的View注册点击事件
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        DepartmentList p = list.get(i);
        viewHolder.name.setText(p.name);

        viewHolder.item_rv.setLayoutManager(new GridLayoutManager(mContext, 3));
//        viewHolder.item_rv.addItemDecoration(new DividerGridItemDecoration(mContext));
        while (true){
            if (p.group.size() % 3 != 0){
                p.group.add(null);
            }else {
                break;
            }
        }
        adapter = new DepartmentAdapter(mContext, p.group);

        adapter.setOnItemClickListener(new DepartmentAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, DepartmentBean d) {
                if(d != null){
                    Intent mIntent = new Intent(mContext, DoctorListActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("DepartmentBean", d);
                    mIntent.putExtras(mBundle);
                    mContext.startActivity(mIntent);
                }
            }
        });
        viewHolder.item_rv.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return list == null ? 0 : list.size();
    }

    // 重写的自定义ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.item_rv)
        RecyclerView item_rv;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}