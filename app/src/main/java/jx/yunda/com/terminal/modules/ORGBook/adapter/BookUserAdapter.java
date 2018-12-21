package jx.yunda.com.terminal.modules.ORGBook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.widget.MyBookView;
import jx.yunda.com.terminal.widget.SelectUserTextView;

public class BookUserAdapter extends RecyclerView.Adapter {
    List<BookUserBean> mList;
    Context mContext;
    OnItemClickListner onItemClickListner;
    List<UserSelectBean> users  = new ArrayList<>();
    public BookUserAdapter(Context context, List<BookUserBean> beans){
        mContext = context;
        mList = beans;
    }
    public void setSelectUsers(List<UserSelectBean> list){
        users.clear();
        users.addAll(list);
    }
    public interface OnItemClickListner{
        void OnItemClick(int position, int status);
    }
    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
    //定义Viewholder
    class MyViewholder extends RecyclerView.ViewHolder  {
        public SelectUserTextView tvPlan;
        public MyBookView bkInfo;

        public MyViewholder(View root) {
            super(root);
            tvPlan = (SelectUserTextView) root.findViewById(R.id.tvPlan);
            bkInfo = (MyBookView) root.findViewById(R.id.bkInfo);
//            root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListner.OnItemClick(getPosition());
//                }
//            });
        }

        public TextView getTvPlan() {
            return tvPlan;
        }

        public MyBookView getBkInfo() {
            return bkInfo;
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_user,parent,false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int index = position;
        final MyViewholder holder1 = (MyViewholder) holder;
        BookUserBean entry = mList.get(position);
        holder1.bkInfo.setOnBookListner(new MyBookView.OnBookListner() {
            @Override
            public void OnBook(int status) {
                mList.get(index).setStatus(status);
                onItemClickListner.OnItemClick(index,status);
            }
        });
        if("已点名".equals(mList.get(index).getType())){
            holder1.bkInfo.setEnabled(false);
        }else {
            holder1.bkInfo.setEnabled(true);
        }
        if(mList.get(index).getStatus()==1){
            holder1.tvPlan.setEnabled(true);
        }else {
            holder1.tvPlan.setEnabled(false);
        }
        holder1.tvPlan.setOnSelectCompleteLisnter(new SelectUserTextView.OnSelectCompleteLisnter() {
            @Override
            public void OnSelectUserComplet() {
                List<UserSelectBean> selects = holder1.tvPlan.getSelectUsers();
                if(selects!=null&&selects.size()>0){
                    String str = "";
                    String id = "";
                    for(int i = 0; i<selects.size();i++){
                        if(i==0){
                            str = str + selects.get(i).getUserName();
                            id = id + selects.get(i).getIdx();
                        }else {
                            str = str+","+selects.get(i).getUserName();
                            id = id+","+selects.get(i).getIdx();
                        }

                    }
                    mList.get(position).setPlan(str);
                    mList.get(position).setPlanid(id);
                    notifyDataSetChanged();
//                    holder1.tvPlan.setText(str+"的任务");
                }
            }
        });
        if(entry!=null){
            holder1.tvPlan.setText(mList.get(index).getPlan());
            holder1.bkInfo.setText(mList.get(index).getEmpname());
            holder1.bkInfo.setStatus(mList.get(index).getStatus());
        }

        if(users.size()>0&&((MyViewholder)holder).tvPlan.IsEmpty()){
            List<UserSelectBean> mlists = new ArrayList<>();
            mlists.addAll(users);
            ((MyViewholder)holder).tvPlan.setUsers(mlists);
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
