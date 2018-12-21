package jx.yunda.com.terminal.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;

public class UserListAdapter extends BaseAdapter {
    Context context;
    List<EmpForForemanDispatch> list;
    public UserListAdapter(Context context,List<EmpForForemanDispatch> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }else {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_users,null);
        CheckBox cbuser = (CheckBox)convertView.findViewById(R.id.cbuser);
        if(list!=null&&list.size()>0){
            cbuser.setText(list.get(position).getEmpname());
            if(list.get(position).getChecked()==null){
                cbuser.setChecked(false);
            }else {
                cbuser.setChecked(list.get(position).getChecked());
            }
        }
        cbuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setChecked(isChecked);
            }
        });
        return convertView;
    }
}
