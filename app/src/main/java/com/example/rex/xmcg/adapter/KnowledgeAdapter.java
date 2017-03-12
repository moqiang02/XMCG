package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Knowledge;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/14.
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> implements View.OnClickListener {

    private List<Knowledge> list;

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public KnowledgeAdapter(Context context, List<Knowledge> list) {
        this.mContext = context;
        this.list = list;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Knowledge data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Knowledge) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public KnowledgeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_knowledge, viewGroup, false);//将创建的View注册点击事件
        v.setOnClickListener(this);
        return new KnowledgeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(KnowledgeAdapter.ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Knowledge p = list.get(i);
        viewHolder.subject.setText(p.subject);
        viewHolder.summary.setText(p.summary);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(p);
    }


    @Override
    public int getItemCount() {
        // 返回数据总数
        return list == null ? 0 : list.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        RoundedImageView avatar;
        @BindView(R.id.subject)
        TextView subject;
        @BindView(R.id.summary)
        TextView summary;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}