package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;

public class SelectDictAdapter extends BaseAdapter {
    List<DictBean> list;
    Context context;
    ViewHolder holder;
    OnSelectedChangedListner onSelectedChangedListner;
    public SelectDictAdapter(Context context, List<DictBean> list){
        this.context = context;
        this.list = list;
    }
    public interface OnSelectedChangedListner{
        void OnselectedChanged(int position, boolean isChecked);
    }
    public void setOnSelectedChangedListner(OnSelectedChangedListner onSelectedChangedListner){
        this.onSelectedChangedListner = onSelectedChangedListner;
    }
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_schedule_select_train, null);
            holder = new ViewHolder();
            holder.cbTainCheck = (CheckBox)convertView.findViewById(R.id.cbTainCheck);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DictBean entry = list.get(position);
        if(entry!=null){
            holder.cbTainCheck.setText(entry.getName());
        }
        holder.cbTainCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entry.setChecked(isChecked);
//                if(isChecked){
//                    onSelectedChangedListner.OnselectedChanged(position,isChecked);
//                }else {
//                    onSelectedChangedListner.OnselectedChanged(position,isChecked);
//                }
            }
        });
        if (!entry.isChecked()) {
            holder.cbTainCheck.setChecked(false);
        } else {
            holder.cbTainCheck.setChecked(true);
        }
        return convertView;
    }

   class ViewHolder {
        CheckBox cbTainCheck;
    }
}
