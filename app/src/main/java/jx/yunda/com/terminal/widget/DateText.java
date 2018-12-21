package jx.yunda.com.terminal.widget;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <li>标题: 机车整备管理信息系统-手持终端
 * <li>说明: 日期选择控件
 * <li>创建人：刘晓斌
 * <li>创建日期：2015年10月17日
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2015 运达科技公司
 * @author 刘晓斌
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public class DateText extends TextView implements OnClickListener, OnDateSetListener {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private DatePickerDialog picker;
    private Date value;
    
    public DateText(Context context) {
        super(context);
        init();
    }
    public DateText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public DateText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        getDateDialog().show();
    }   

    DatePickerDialog getDateDialog(){
        if(picker != null)  return picker;
        final Calendar c = Calendar.getInstance();
        picker = new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        return picker;
    }
    
    public Date getValue(){
        return value;
    }
    
    public void setValue(Date value) {
        this.value = value;
        if(value == null)   this.setText("");
        else    this.setText(format.format(value));
    }
    
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        value = c.getTime();
        setValue(value);
    }
    
}
