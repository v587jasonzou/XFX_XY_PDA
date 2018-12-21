package jx.yunda.com.terminal.modules.ORGBookNew.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookUserBeanNew;
import jx.yunda.com.terminal.modules.ORGBookNew.model.Nodebean;

public class UserPlanAdapter extends RecyclerView.Adapter {
    List<Nodebean> mList;
    Context mContext;
    OnItemClickListner onItemClickListner;


    public UserPlanAdapter(Context context, List<Nodebean> beans) {
        mContext = context;
        mList = beans;
    }

    public interface OnItemClickListner {
        void OnItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    //定义Viewholder
    class MyViewholder extends RecyclerView.ViewHolder {
        public TextView tvTrainInfo;
        public TextView tvPlanInfo;
        public ImageView ivDelete;

        public MyViewholder(View root) {
            super(root);
            tvTrainInfo = (TextView) root.findViewById(R.id.tvTrainInfo);
            tvPlanInfo = (TextView) root.findViewById(R.id.tvPlanInfo);
            ivDelete = (ImageView)root.findViewById(R.id.ivDelete);
        }

        public TextView getTvPlanInfo() {
            return tvPlanInfo;
        }

        public TextView getTvTrainInfo() {
            return tvTrainInfo;
        }

        public ImageView getIvDelete() {
            return ivDelete;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_user_plans, parent, false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int index = position;
        final MyViewholder holder1 = (MyViewholder) holder;
        Nodebean entry = mList.get(position);

        if (entry != null) {
            if(entry.getTrainTypeShortName()!=null&&entry.getTrainNo()!=null){
                holder1.tvTrainInfo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo());
            }else {
                holder1.tvTrainInfo.setText("");
            }
            if(entry.getNodeName()!=null){
                holder1.tvPlanInfo.setText(entry.getNodeName());
            }
        }
        holder1.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.OnItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }
}
