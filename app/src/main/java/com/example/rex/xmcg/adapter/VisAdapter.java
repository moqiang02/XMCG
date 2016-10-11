package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Vis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/11.
 */

public class VisAdapter extends RecyclerView.Adapter<VisAdapter.ViewHolder> {

    private List<Vis> vises;

    private Context mContext;

    public VisAdapter(Context context, List<Vis> vises) {
        this.mContext = context;
        this.vises = vises;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vis, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Vis p = vises.get(i);
        viewHolder.day.setText(p.day+"天");
        viewHolder.name.setText(p.doctorName);
        viewHolder.department.setText(p.deptName);
        viewHolder.num.setText(p.regNumber);
        viewHolder.person.setText(p.name);
        viewHolder.time.setText(p.opdDate + " "+p.estiTime);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return vises == null ? 0 : vises.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.department)
        TextView department;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.person)
        TextView person;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}