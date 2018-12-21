package jx.yunda.com.terminal.modules.ORGBookNew.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookUserBeanNew;
import jx.yunda.com.terminal.widget.MyBookView;
import jx.yunda.com.terminal.widget.SelectUserTextView;

public class BookUserAdapterNew extends RecyclerView.Adapter {
    List<BookUserBeanNew> mList;
    Context mContext;
    OnItemClickListner onItemClickListner;
    public BookUserAdapterNew(Context context, List<BookUserBeanNew> beans){
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
        public TextView bkInfo,tvfwhNum;

        public MyViewholder(View root) {
            super(root);
            bkInfo = (TextView) root.findViewById(R.id.bkInfo);
            tvfwhNum = (TextView)root.findViewById(R.id.tvfwhNum);
//            root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListner.OnItemClick(getPosition());
//                }
//            });
        }


        public TextView getBkInfo() {
            return bkInfo;
        }

        public TextView getTvfwhNum() {
            return tvfwhNum;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_user_new,parent,false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int index = position;
        final MyViewholder holder1 = (MyViewholder) holder;
        BookUserBeanNew entry = mList.get(position);

        if(entry!=null){
//            holder1.tvPlan.setText(mList.get(index).getPlan());
            holder1.bkInfo.setText(mList.get(index).getEmpName());
            if(entry.getStates()==0){
                holder1.bkInfo.setTextColor(ContextCompat.getColor(mContext,R.color.black));
                holder1.bkInfo.setBackgroundResource(R.color.white);
            }else {
                holder1.bkInfo.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                holder1.bkInfo.setBackgroundResource(R.color.colorPrimary);
            }
            if(entry.getFwhNum()!=null&&entry.getFwhNum()>0){
                holder1.tvfwhNum.setVisibility(View.VISIBLE);
                if(entry.getJt28Num()!=null&&entry.getJt28Num().shortValue()>0){
                    holder1.tvfwhNum.setText(entry.getFwhNum()+"/"+entry.getJt28Num());
                }else {
                    holder1.tvfwhNum.setText(entry.getFwhNum()+"/0");
                }
            }else {
                if(entry.getJt28Num()!=null&&entry.getJt28Num().shortValue()>0){
                    holder1.tvfwhNum.setVisibility(View.VISIBLE);
                    holder1.tvfwhNum.setText("0/"+entry.getJt28Num());
                }else {
                    holder1.tvfwhNum.setVisibility(View.GONE);
                }
            }

        }
        holder1.bkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.OnItemClick(position);
            }
        });


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
