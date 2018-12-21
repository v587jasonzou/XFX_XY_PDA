package jx.yunda.com.terminal.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jx.yunda.com.terminal.R;

public class MyBookOrgView extends LinearLayout {
    private TextView tvInfo;
    private ImageView ivRight;
    public int position = 0;
    OnBookListner onTabSelectListner;
    Context mContext;
    public interface OnBookListner{
        void OnBook(int position);
    }
    public void setOnBookListner(OnBookListner onTabSelectListner){
        this.onTabSelectListner = onTabSelectListner;
    }
    public MyBookOrgView(Context context) {
        super(context);
    }

    public MyBookOrgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    public void setText(String info){
        tvInfo.setText(info);
    }
    public int getStatus(){
        return position;
    }
    public void setStatus(int position){
        this.position = position;
        if(position==1){
            ivRight.setVisibility(VISIBLE);
            tvInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_green_bg));
        }else {
            ivRight.setVisibility(GONE);
            tvInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }
    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.view_book_orgs,this);
        tvInfo = (TextView)findViewById(R.id.tvInfo);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    ivRight.setVisibility(VISIBLE);
                    position = 1;
                    tvInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_green_bg));
                }else {
                    ivRight.setVisibility(GONE);
                    position = 0;
                    tvInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                onTabSelectListner.OnBook(position);
            }
        });
    }
}
