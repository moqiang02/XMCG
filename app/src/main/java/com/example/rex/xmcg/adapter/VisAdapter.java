package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Vis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/10/11.
 */

public class VisAdapter extends RecyclerView.Adapter<VisAdapter.ViewHolder> {

    private List<Vis> vises;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public VisAdapter(Context context, List<Vis> vises) {
        this.mContext = context;
        this.vises = vises;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Vis data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vis, viewGroup, false);//将创建的View注册点击事件
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Vis p = vises.get(i);
        viewHolder.day.setText(p.day + "天");
        viewHolder.name.setText(p.doctorName);
        viewHolder.department.setText(p.deptName);
        viewHolder.num.setText(p.regNumber);
        viewHolder.person.setText(p.name);
        viewHolder.time.setText(p.opdDate + " " + p.estiTime);

        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.cancle.setTag(p);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return vises == null ? 0 : vises.size();
    }

    // 重写的自定义ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.cancle)
        Button cancle;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


        @OnClick(R.id.cancle)
        public void cancle(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, (Vis) v.getTag());
            }

        }
    }
}