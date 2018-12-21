package jx.yunda.com.terminal.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jx.yunda.com.terminal.R;

public class MyTablayout extends LinearLayout {
    private TextView tvLeft,tvRight,tvCenter;
    public int position = 0;
    OnTabSelectListner onTabSelectListner;
    public interface OnTabSelectListner{
        void OnTabClick(int position);
    }
    public void setOnTabSelectListner(OnTabSelectListner onTabSelectListner){
        this.onTabSelectListner = onTabSelectListner;
    }
    public MyTablayout(Context context) {
        super(context);
    }

    public MyTablayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void setText(String left,String center,String right){
        tvLeft.setText(left);
        tvRight.setText(right);
        tvCenter.setText(center);
    }
    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_mytabview,this);
        tvLeft = (TextView)findViewById(R.id.tvLeft);
        tvRight = (TextView)findViewById(R.id.tvRight);
        tvCenter = (TextView)findViewById(R.id.tvCenter);
        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                tvLeft.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_click));
                tvRight.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                tvCenter.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                onTabSelectListner.OnTabClick(position);
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;
                tvLeft.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                tvRight.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_click));
                tvCenter.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                onTabSelectListner.OnTabClick(position);
            }
        });
        tvCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                tvLeft.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                tvRight.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_unclick));
                tvCenter.setBackgroundColor(ContextCompat.getColor(context, R.color.tab_click));
                onTabSelectListner.OnTabClick(position);
            }
        });
    }
}
