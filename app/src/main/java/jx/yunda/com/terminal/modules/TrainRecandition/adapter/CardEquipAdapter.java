package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.WorkCardBean;

public class CardEquipAdapter extends RecyclerView.Adapter {
    List<WorkCardBean> mList;
    LayoutInflater inflater;
    Context mContext;
    OnItemClickListner onItemClickListner;


    public CardEquipAdapter(Context context, List<WorkCardBean> beans) {
        inflater = LayoutInflater.from(context);
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
        TextView tvCardNum;
        TextView tvEquipName;
        ImageView ivRight;

        public MyViewholder(View root) {
            super(root);
            tvCardNum = (TextView) root.findViewById(R.id.tvCardNum);
            tvEquipName = (TextView) root.findViewById(R.id.tvEquipName);
            ivRight = (ImageView) root.findViewById(R.id.ivRight);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListner.OnItemClick(getPosition());
                }
            });
        }

        public TextView getTvCardNum() {
            return tvCardNum;
        }

        public TextView getTvEquipName() {
            return tvEquipName;
        }

        public ImageView getIvRight() {
            return ivRight;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_equip, parent, false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewholder holder1 = (MyViewholder) holder;
        if (mList != null && mList.size() > 0) {
            if (mList.get(position).getWorkCardSeq() != null)
                holder1.tvCardNum.setText(mList.get(position).getWorkCardSeq());
            holder1.tvEquipName.setText(mList.get(position).getWorkCardName());
        }
        if("COMPLETE".equals(mList.get(position).getStatus())){
            holder1.ivRight.setVisibility(View.VISIBLE);
        }else {
            holder1.ivRight.setVisibility(View.GONE);
        }
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
