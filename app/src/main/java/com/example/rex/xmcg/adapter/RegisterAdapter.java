package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Register;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/14.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

    private List<Register> list;

    private Context mContext;

    public RegisterAdapter(Context context, List<Register> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_register, viewGroup, false);
        return new RegisterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RegisterAdapter.ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Register p = list.get(i);
        viewHolder.name.setText(p.doctorName);
        viewHolder.deptname.setText(p.deptName);
//        viewHolder.status.setText(p.regNumber);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return list == null ? 0 : list.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.deptname)
        TextView deptname;
        @BindView(R.id.status)
        TextView status;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}