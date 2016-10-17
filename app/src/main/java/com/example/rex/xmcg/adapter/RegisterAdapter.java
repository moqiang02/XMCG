package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/14.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> implements View.OnClickListener {

    private List<Doctor> list;

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RegisterAdapter(Context context, List<Doctor> list) {
        this.mContext = context;
        this.list = list;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Doctor data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Doctor) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_register, viewGroup, false);//将创建的View注册点击事件
        v.setOnClickListener(this);
        return new RegisterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RegisterAdapter.ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Doctor p = list.get(i);
        viewHolder.name.setText(p.doctorName);
        viewHolder.deptname.setText(p.deptName);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(p);
        if (p.canReg.equals("N")) {
            viewHolder.status.setText("停诊");
            viewHolder.status.setBackgroundResource(R.drawable.register_state_gray_bg);
        } else if (p.isFull.equals("Y")) {
            viewHolder.status.setText("已满");
            viewHolder.status.setBackgroundResource(R.drawable.register_state_red_bg);
        } else {
            viewHolder.status.setText("有号");
            viewHolder.status.setBackgroundResource(R.drawable.register_state_green_bg);
        }

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