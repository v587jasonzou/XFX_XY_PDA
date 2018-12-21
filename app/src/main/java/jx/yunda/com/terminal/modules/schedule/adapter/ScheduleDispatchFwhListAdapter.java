package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class ScheduleDispatchFwhListAdapter extends BaseAdapter {
    Context mContext;
    List<FwhBean> mlist;
    OnViewClickInItemListner onViewClickListner;
    int status = 0;
    List<UserSelectBean> users;
    List<UserSelectBean> usersAll;
    public ScheduleDispatchFwhListAdapter(Context context, List<FwhBean> list) {
        mContext = context;
        mlist = list;
    }

    public void setOnViewClickListner(OnViewClickInItemListner onViewClickListner){
        this.onViewClickListner = onViewClickListner;
    }
    public void setStatus(int stut){
        status = stut;
    }
    public void setUsers(List<UserSelectBean> users){
        if(users!=null&&users.size()>0){
            this.users.addAll(users);
            usersAll.addAll(users);
        }
    }
    @Override
    public int getCount() {
        if (mlist != null && mlist.size() > 0) {
            return mlist.size();
        } else {
            return 0;
        }
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bookdispatch_fwh, null);
        TextView tvFwhLocation = (TextView)convertView.findViewById(R.id.tvFwhLocation);
        TextView tvOperator = (TextView)convertView.findViewById(R.id.tvOperator);
        Button btnDispatch = (Button)convertView.findViewById(R.id.btn_dispatch_fwh);
        LinearLayout llInfo = (LinearLayout)convertView.findViewById(R.id.llInfo_fwh);
        LinearLayout ll_redispatch_fwh = (LinearLayout)convertView.findViewById(R.id.ll_redispatch_fwh);
        LinearLayout llworker = (LinearLayout)convertView.findViewById(R.id.llworker);
        ImageView iv_redispatch_fwh = (ImageView)convertView.findViewById(R.id.iv_redispatch_fwh);
        TextView tv_status_fwh = (TextView)convertView.findViewById(R.id.tv_status_fwh);
        if(status==0){
            btnDispatch.setVisibility(View.VISIBLE);
            llworker.setVisibility(View.GONE);
        }else {
            btnDispatch.setVisibility(View.GONE);
            llworker.setVisibility(View.VISIBLE);
        }
//        llInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onViewClickListner.onViewClick(v, position);
//            }
//        });
        if(mlist!=null&&mlist.size()>0){
            if(mlist.get(position)!=null){
                FwhBean bean = mlist.get(position);
                if(!TextUtils.isEmpty(bean.getNodeName())){
                    tvFwhLocation.setText(bean.getNodeName());
                }

                if(!TextUtils.isEmpty(bean.getWorkStationBelongTeamName()) || !TextUtils.isEmpty(bean.getWorkerNameStr())){
                    tvOperator.setText(TextUtils.isEmpty(bean.getWorkerNameStr()) ? bean.getWorkStationBelongTeamName() : bean.getWorkerNameStr());
                }
                if(status==1){
                    ll_redispatch_fwh.setVisibility(View.VISIBLE);
                    if("NOTSTARTED".equals(bean.getStatus())){
                        iv_redispatch_fwh.setVisibility(View.VISIBLE);
                    }else if("COMPLETED".equals(bean.getStatus())){
                        iv_redispatch_fwh.setVisibility(View.GONE);
                        tv_status_fwh.setText("已完成");
                        tv_status_fwh.setTextColor(ContextCompat.getColor(mContext, R.color.icon_green));
                    }
                }else {
                    ll_redispatch_fwh.setVisibility(View.GONE);
                }
                ll_redispatch_fwh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onViewClickListner != null) {
                            onViewClickListner.onViewClick(v, position);
                        }
                    }
                });
                btnDispatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onViewClickListner != null) {
                            onViewClickListner.onViewClick(view, position);
                        }
                    }
                });
            }
        }

        return convertView;
    }

}
