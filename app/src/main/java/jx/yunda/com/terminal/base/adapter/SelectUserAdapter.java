package jx.yunda.com.terminal.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.entity.UserSelectBean;

public class SelectUserAdapter extends BaseAdapter {
    List<UserSelectBean> users;
    Context mContext;
    Viewholder holder;
    public SelectUserAdapter(Context context, List<UserSelectBean> users){
        this.users = users;
        mContext = context;
    }
    @Override
    public int getCount() {
        if(users!=null){
            return users.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        final int index = position;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_users,null);
            holder = new Viewholder();
            holder.cbuser = (CheckBox)convertView.findViewById(R.id.cbuser);
            convertView.setTag(holder);
        }else {
            holder = (Viewholder)convertView.getTag();
        }
        holder.cbuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                users.get(index).setSelected(isChecked);
            }
        });
        holder.cbuser.setText(users.get(position).getUserName());
        if (users.get(index).getSelected()){
            holder.cbuser.setChecked(true);
        }else {
            holder.cbuser.setChecked(false);
        }
        return convertView;
    }
    class Viewholder{
        CheckBox cbuser;
    }
}
