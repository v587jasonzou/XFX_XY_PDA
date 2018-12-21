package jx.yunda.com.terminal.modules.fixflow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowTrainBean;

public class FlowTrainAdapter extends BaseAdapter {
    Context context;
    List<FlowTrainBean> list;
    ViewHolder holder;
    public FlowTrainAdapter(Context context, List<FlowTrainBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_flow_train_list, null);
            holder = new ViewHolder();
            holder.FwhInfo = (TextView)convertView.findViewById(R.id.FwhInfo);
            holder.tvTrainNo = (TextView)convertView.findViewById(R.id.tvTrainNo);
            holder.tvReceiveTime = (TextView)convertView.findViewById(R.id.tvReceiveTime);
            holder.tvUser = (TextView)convertView.findViewById(R.id.tvUser);
            holder.TpInfo = (TextView)convertView.findViewById(R.id.TpInfo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        FlowTrainBean entry = list.get(position);
        if(entry!=null){
            if(entry.getTrainNo()!=null){
                if(!TextUtils.isEmpty(entry.getRepairClassName())){
                    holder.tvTrainNo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo()+"("+entry.getRepairClassName()+")");
                }else {
                    holder.tvTrainNo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo());
                }
            }else {
                holder.tvTrainNo.setText("");
            }
            if(!TextUtils.isEmpty(entry.getPlanBeginTime())){
                holder.tvReceiveTime.setText(entry.getPlanBeginTime());
            }else {
                holder.tvReceiveTime.setText("");
            }
            if(entry.getDeliverEmpName()!=null){
                holder.tvUser.setText(entry.getDeliverEmpName());
            }else {
                holder.tvUser.setText("");
            }
            holder.FwhInfo.setText("("+entry.getFwFinishedNo()+"/"+(entry.getFwFinishedNo()+entry.getFwUnfinishedNo()+")"));
            holder.TpInfo.setText("("+entry.getTpFinishedNo()+"/"+(entry.getTpFinishedNo()+entry.getTpUnfinishedNo()+")"));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTrainNo;
        TextView tvReceiveTime;
        TextView tvUser;
        TextView FwhInfo;
        TextView TpInfo;
    }
}
