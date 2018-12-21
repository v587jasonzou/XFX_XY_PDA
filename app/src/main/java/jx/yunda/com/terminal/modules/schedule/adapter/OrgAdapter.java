package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.widget.MyBookOrgView;


public class OrgAdapter extends BaseAdapter {
    List<OrgBean> mList;
    Context context;
    ViewHolder holder;
    public OrgAdapter(Context context, List<OrgBean> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book_org, null);
            holder = new ViewHolder();
            holder.bkInfo = (MyBookOrgView)convertView.findViewById(R.id.bkInfo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bkInfo.setOnBookListner(new MyBookOrgView.OnBookListner() {
            @Override
            public void OnBook(int status) {
                mList.get(position).setStatus(status);
            }
        });

        if(mList!=null&&mList.size()>0){
            if(mList.get(position).getHandeOrgName()!=null){
                holder.bkInfo.setText(mList.get(position).getHandeOrgName());
                holder.bkInfo.setStatus(mList.get(position).getStatus());
            }
        }

        return convertView;
    }

     class ViewHolder {
         MyBookOrgView bkInfo;
    }
}
