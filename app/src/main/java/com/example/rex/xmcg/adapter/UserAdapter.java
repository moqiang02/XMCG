package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/10/11.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Map<String, Boolean> map;

    public UserAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.users = users;
        map = new HashMap<String, Boolean>();
        for (User u : users) {
            map.put(u.name,false);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, User data);
    }

    public void checkCurrent(User user){
        for (User u : users) {
            map.put(u.name,false);
        }
        map.put(user.name,true);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);//将创建的View注册点击事件
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        User p = users.get(i);
        viewHolder.name.setText(p.name);
        if (map.size() > 0) {
            viewHolder.name.setChecked(map.get(p.name));
        }

        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.name.setTag(p);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return users == null ? 0 : users.size();
    }

    // 重写的自定义ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        CheckBox name;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


        @OnClick(R.id.name)
        public void check(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, (User) v.getTag());
            }

        }
    }
}