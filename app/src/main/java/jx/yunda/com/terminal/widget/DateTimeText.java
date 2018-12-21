package jx.yunda.com.terminal.widget;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <li>标题: 机车整备管理信息系统-手持终端
 * <li>说明: 日期时间选择控件
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
public class DateTimeText extends TextView implements OnClickListener, OnDateSetListener, OnTimeSetListener {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Date value;
    private Calendar calendar;
    private boolean isShowTime = true;
    public void setIsShowTime(boolean is){
        isShowTime = is;
    }
    public DateTimeText(Context context) {
        super(context);
        init();
    }
    public DateTimeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public DateTimeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        calendar = Calendar.getInstance();
        getDateDialog().show();
    }   

    DatePickerDialog getDateDialog(){
        if(datePicker != null)  return datePicker;
        datePicker = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return datePicker;
    }
    
    TimePickerDialog getTimeDialog(){
        if(timePicker != null)  return timePicker;
        timePicker = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.SECOND), true);
        return timePicker;
    }
    
    public Date getValue(){
        return value;
    }
    
    public void setValue(Date value) {
        this.value = value;
        if(value == null){
            this.setText("");
        }
        else {
            if(isShowTime){
                this.setText(format.format(value));
            }else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                this.setText(format.format(value));
            }

        }
    }
    
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        if(isShowTime){
            getTimeDialog().show();
        }else {
            value = calendar.getTime();
            setValue(value);
        }

    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        value = calendar.getTime();
        setValue(value);
    }
    
}
