package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;

public class BookOrgAdapter extends BaseAdapter {
    public Context mContext;
    public List<ScheduleTrainBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;

    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
    }

    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter) {
        this.onBtnClickLisnter = onBtnClickLisnter;
    }

    public BookOrgAdapter(Context context, List<ScheduleTrainBean> list) {
        mContext = context;
        mlist = list;
    }

    @Override
    public int getCount() {
        if (mlist != null) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_train, null);
            holder = new ViewHolder();
            holder.tvTrainNo = (TextView)convertView.findViewById(R.id.tvTrainNo);
            holder.tvTrainType = (TextView)convertView.findViewById(R.id.tvTrainType);
            holder.tvTrainFix = (TextView)convertView.findViewById(R.id.tvTrainFix);
            holder.tvTrainPlace = (TextView)convertView.findViewById(R.id.tvTrainPlace);
            holder.tvBook = (TextView)convertView.findViewById(R.id.tvBook);
            holder.tvSearch = (TextView)convertView.findViewById(R.id.tvSearch);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(mlist.get(position).getTrainNo()!=null){
            holder.tvTrainNo.setText(mlist.get(position).getTrainNo());
        }
        if(mlist.get(position).getTrainTypeShortName()!=null){
            holder.tvTrainType.setText(mlist.get(position).getTrainTypeShortName());
        }
        if(mlist.get(position).getRepairClassName()!=null){
            holder.tvTrainFix.setText(mlist.get(position).getRepairClassName());
        }
        if(mlist.get(position).getRcTrainCategoryCode()!=null){
            holder.tvTrainPlace.setText(mlist.get(position).getRcTrainCategoryCode()+"");
        }
        if(mlist.get(position).getIsCallName()==1){
            holder.tvBook.setText("调度");
        }else {
            holder.tvBook.setText("点名");
        }
        holder.tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlist.get(position).getIsCallName()==1){
                    onBtnClickLisnter.OnBtnClick(position,"调度");
                }else {
                    onBtnClickLisnter.OnBtnClick(position,"点名");
                }

            }
        });
        if(mlist.get(position).getBeginTime()==null||"".equals(mlist.get(position).getBeginTime())){
            holder.tvSearch.setVisibility(View.VISIBLE);
        }else {
            holder.tvSearch.setVisibility(View.INVISIBLE);
        }
        holder.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClickLisnter.OnBtnClick(position,"上台");
            }
        });
        return convertView;
    }



    class ViewHolder {
        TextView tvTrainType;
        TextView tvTrainNo;
        TextView tvTrainFix;
        TextView tvTrainPlace;
        TextView tvBook;
        TextView tvSearch;

    }
}
