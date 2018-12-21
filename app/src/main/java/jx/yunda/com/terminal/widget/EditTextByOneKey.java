package jx.yunda.com.terminal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import jx.yunda.com.terminal.R;

/**
 * <li>标题: 机车检修整备管理信息系统-手持终端 
 * <li>说明: 带一键清除的输入框控件 
 * <li>创建人：刘晓斌 
 * <li>创建日期：2016年1月7日 
 * <li>修改人: 
 * <li>修改日期： 
 * <li>修改内容： 
 * <li>版权: Copyright (c) 2015 运达科技公司
 */
public class EditTextByOneKey extends EditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {
    
    private Drawable mClearTextIcon;

    private OnFocusChangeListener mOnFocusChangeListener;
    
    private OnTouchListener mOnTouchListener;
    
    public EditTextByOneKey(final Context context) {
        super(context);
        init(context);
    }
    
    public EditTextByOneKey(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public EditTextByOneKey(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(final Context context) {
        final Drawable drawable = ContextCompat.getDrawable(context,R.mipmap.clear_icon);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable); // Wrap the drawable so that it can be tinted pre Lollipop
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        mClearTextIcon = wrappedDrawable;
        mClearTextIcon.setBounds(0, 0, mClearTextIcon.getIntrinsicHeight(), mClearTextIcon.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    
    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mOnFocusChangeListener = l;
    }
    
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mOnTouchListener = l;
    }
    
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearTextIcon.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText("");
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
    }
    
    @Override
    public final void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (isFocused()) {
            setClearIconVisible(text.length() > 0);
        }
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
    }
    
    @Override
    public void afterTextChanged(Editable s) {
        
    }
    
    private void setClearIconVisible(final boolean visible) {
        mClearTextIcon.setVisible(visible, false);
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], visible ? mClearTextIcon : null, compoundDrawables[3]);
    }
}
