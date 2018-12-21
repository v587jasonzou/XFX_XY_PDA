package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.utils.SizeConvert;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class ScheduleBookAdapter extends RecyclerView.Adapter {
    List<OrgBean> mList;
    Context mContext;
    OnItemClickListner onItemClickListner;
    public ScheduleBookAdapter(Context context, List<OrgBean> beans){
        mContext = context;
        mList = beans;
    }
    public interface OnItemClickListner{
        void OnItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
    //定义Viewholder
    class MyViewholder extends RecyclerView.ViewHolder  {
        public LinearLayout tvPlan,llcontent;
        public TextView bkInfo;

        public MyViewholder(View root) {
            super(root);
            tvPlan = (LinearLayout) root.findViewById(R.id.tvPlan);
            bkInfo = (TextView) root.findViewById(R.id.bkInfo);
            llcontent = (LinearLayout)root.findViewById(R.id.llcontent);
//            root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListner.OnItemClick(getPosition());
//                }
//            });
        }

        public LinearLayout getTvPlan() {
            return tvPlan;
        }

        public TextView getBkInfo() {
            return bkInfo;
        }

        public LinearLayout getLlcontent() {
            return llcontent;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_train,parent,false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int index = position;
        final MyViewholder holder1 = (MyViewholder) holder;
        OrgBean entry = mList.get(position);
        holder1.llcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.OnItemClick(index);
            }
        });

        if(entry!=null){
            holder1.bkInfo.setText(mList.get(index).getHandeOrgName());
//            if(entry.getTrainList()!=null&&entry.getTrainList().size()>0){
//                List<ScheduleTrainBean> trainBeans = entry.getTrainList();
//                String str = "";
//                for(int i = 0;i<trainBeans.size();i++){
//                    if(trainBeans.get(i).getStatus()==1){
//                        String trainInfo = trainBeans.get(i).getTrainTypeShortName()+" "+trainBeans.get(i).getTrainNo();
//                        if(i == 0){
//                            str = str+trainInfo;
//                        }else {
//                            str = str+","+trainInfo;
//                        }
//                    }
//                }
//                holder1.tvPlan.setText(str);
//            }else {
//                holder1.tvPlan.setText("");
//            }
            if(entry.getTrainList()!=null&&entry.getTrainList().size()>0){
                holder1.tvPlan.removeAllViews();
                List<ScheduleTrainBean> trainBeans = new ArrayList<>();
                for(ScheduleTrainBean bean:mList.get(position).getTrainList()){
                    if(bean.getStatus()==1){
                        trainBeans.add(bean);
                    }
                }
                //动态添加View
                int wighth = Utils.CellNumber(trainBeans.size(),3);
                for(int i = 0;i<wighth;i++){
                    LinearLayout linearLayout = new LinearLayout(mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 10);
                    param.setMargins(0, 10, 0, 10);
                    linearLayout.setPadding(0,SizeConvert.dip2px(5),0,SizeConvert.dip2px(5));
                    for(int j = i*3;j<(i+1)*3;j++){
                        if(j>trainBeans.size()-1){
                            break;
                        }
                        TextView textView = new TextView(mContext);
                        LinearLayout tvlinearLayout = new LinearLayout(mContext);
                        tvlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams tvllParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        tvllParams.gravity = Gravity.CENTER;
                        tvlinearLayout.setLayoutParams(tvllParams);
                        tvlinearLayout.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        //设置textview垂直居中
                        tvParams.gravity = Gravity.CENTER;
                        textView.setLayoutParams(tvParams);
                        textView.setPadding(12,6,12,6);
                        textView.setTextSize(12);
                        textView.setGravity(Gravity.CENTER);
                        textView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                        textView.setBackgroundResource(R.drawable.book_button_bg);
                        textView.setText(trainBeans.get(j).getTrainTypeShortName()+" "+trainBeans.get(j).getTrainNo());
                        tvlinearLayout.addView(textView);
                        linearLayout.addView(tvlinearLayout);
                    }
                    holder1.tvPlan.addView(linearLayout);
                }

            }else {
                holder1.tvPlan.removeAllViews();
            }

        }
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }else {
            return 0;
        }
    }
}
