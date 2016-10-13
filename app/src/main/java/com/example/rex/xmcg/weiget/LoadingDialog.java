package com.example.rex.xmcg.weiget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rex.xmcg.R;

/**
 * Created by Rex on 2016/10/13.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        this(context,"");
    }

    public LoadingDialog(Context context,String msg) {
        super(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progressdialog_no_deal, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.anim);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (msg.equals("")){
            tipTextView.setVisibility(View.GONE);
        }else{
            tipTextView.setText(msg);// 设置加载信息
        }

        setCancelable(false);// 可以用“返回键”取消
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }




}
