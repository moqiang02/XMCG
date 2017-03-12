package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.DepartmentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/11.
 */

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {

    private List<DepartmentBean> list;
    private Context mContext;

    public DepartmentAdapter(Context context, List<DepartmentBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_department, viewGroup, false);//将创建的View注册点击事件
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        DepartmentBean p = list.get(i);
        viewHolder.name.setText(p.name);

        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.name.setTag(p);
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

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}