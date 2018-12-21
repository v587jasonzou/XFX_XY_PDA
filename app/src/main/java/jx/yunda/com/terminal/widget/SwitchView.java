package jx.yunda.com.terminal.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.utils.SizeConvert;

/**
 * 名称：自定义开关控件
 * 创建人：周雪巍
 * 时间：2018年07月18日
 *
 * */
public class SwitchView extends LinearLayout {
    TextView tvLeft;
    ImageView ivWhiteCircle;
    TextView tvRight;
    Context mContext;
    OnSwitchChangeListner onSwitchChangeListner;
    RelativeLayout rlSwitchView;
    public int state = 0;

    public SwitchView(Context context) {
        super(context);
    }
    //开关变动方法
    public void setOnSwitchChangeListner(OnSwitchChangeListner onSwitchChangeListner){
        this.onSwitchChangeListner = onSwitchChangeListner;
    }
    public interface OnSwitchChangeListner{
        void OnSwichChanged(int state);
    }
    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.layout_switchview, null);
        ivWhiteCircle = (ImageView) myView.findViewById(R.id.ivWhiteCircle);
        tvLeft = (TextView) myView.findViewById(R.id.tvLeft);
        tvRight = (TextView) myView.findViewById(R.id.tvRight);
        rlSwitchView = (RelativeLayout)myView.findViewById(R.id.rlSwitchView);
        setView();
        setAnimation();
        addView(myView);
    }
    public void setSwitchText(String left,String right){
        if(left!=null){
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(left);
        }else {
            tvLeft.setVisibility(GONE);
        }
        if(right!=null){
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(right);
        }else {
            tvRight.setVisibility(GONE);
        }
    }
    private void setView() {
        tvLeft.setTextColor(ContextCompat.getColor(mContext, R.color.textviewBg));
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        rlSwitchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 0) {
                    if(!animator2.isRunning()){
                        animator1.start();
                        state = 1;
                        tvLeft.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.textviewBg));
                    }
                } else {
                    if(!animator1.isRunning()){
                        animator2.start();
                        state = 0;
                        tvLeft.setTextColor(ContextCompat.getColor(mContext, R.color.textviewBg));
                        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    }

                }
                if(onSwitchChangeListner!=null){
                    onSwitchChangeListner.OnSwichChanged(state);
                }
            }
        });
    }

    ObjectAnimator animator1, animator2;

    private void setAnimation() {
        // 创建动画作用对象：此处以Button为例
        float curTranslationX = ivWhiteCircle.getTranslationX();
        float with = SizeConvert.dip2px(mContext, 20);
        // 获得当前按钮的位置
        animator1 = ObjectAnimator.ofFloat(ivWhiteCircle, "translationX", curTranslationX, with);

        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴平移（在Y轴上平移同理，采用属性"translationY"
        // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置
        animator1.setDuration(350);

        // 获得当前按钮的位置
        animator2 = ObjectAnimator.ofFloat(ivWhiteCircle, "translationX", with, curTranslationX);

        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴平移（在Y轴上平移同理，采用属性"translationY"
        // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置
        animator2.setDuration(350);

    }
}
