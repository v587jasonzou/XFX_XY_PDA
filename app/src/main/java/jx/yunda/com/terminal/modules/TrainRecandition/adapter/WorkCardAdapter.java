package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;

public class WorkCardAdapter extends RecyclerView.Adapter {
    List<OrderBean> mList;
    LayoutInflater inflater;
    Context mContext;
    OnItemClickListner onItemClickListner;
    public  WorkCardAdapter(Context context,List<OrderBean> beans){
        inflater = LayoutInflater.from(context);
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
        public TextView tvPlanName;
        public ImageView ivPen,ivCamera;

        public MyViewholder(View root) {
            super(root);
            tvPlanName = (TextView) root.findViewById(R.id.tvPlanName);
            ivPen = (ImageView) root.findViewById(R.id.ivPen);
            ivCamera = (ImageView) root.findViewById(R.id.ivCamera);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListner.OnItemClick(getPosition());
                }
            });
        }

        public TextView getTvPlanName() {
            return tvPlanName;
        }

        public ImageView getIvCamera() {
            return ivCamera;
        }

        public ImageView getIvPen() {
            return ivPen;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_workcard,parent,false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewholder holder1 = (MyViewholder) holder;
        if(mList!=null&&mList.get(position)!=null){
            holder1.tvPlanName.setText(mList.get(position).getWorkTaskName());
            if("COMPLETE".equals(mList.get(position).getStatus())){
                holder1.ivPen.setVisibility(View.VISIBLE);
                holder1.ivCamera.setVisibility(View.GONE);
                holder1.ivPen.setImageResource(R.mipmap.right_icon);
            }else {
                if(mList.get(position).getIsPhotograph()!=null){
                    holder1.ivCamera.setVisibility(View.VISIBLE);
//            holder1.ivCamera.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.camera_green));
                    holder1.ivCamera.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.camera_yellow));
                }else {
                    holder1.ivCamera.setVisibility(View.GONE);
                }
                if(mList.get(position).getDetectCount()!=null){
                    holder1.ivPen.setVisibility(View.VISIBLE);
//            holder1.ivPen.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.pen_green));
                    holder1.ivPen.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.pen_yellow));
                }else {
                    holder1.ivPen.setVisibility(View.GONE);
                }
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
