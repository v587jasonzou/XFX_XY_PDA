package jx.yunda.com.terminal.modules.receiveTrain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.ORGBook.adapter.BookUserAdapter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.widget.MyBookView;

public class ReceivedTrainAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<ReceivedTrain> mList;
    public ReceivedTrainAdapter(Context context, List<ReceivedTrain> ReceivedTrain){
        mContext = context;
        mList = ReceivedTrain;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.receive_train_item,parent,false);
        ReceivedTrainAdapter.ReceivedTrainHolder viewholder = new ReceivedTrainAdapter.ReceivedTrainHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReceivedTrain entity = null;
        if(mList!=null&&mList.size()>0){
            entity = mList.get(position);
        }
        ReceivedTrainHolder holder1 = (ReceivedTrainHolder) holder;
        if(entity!=null){
            if(entity.getTrainTypeShortName()!=null&&entity.getTrainNo()!=null){
                holder1.train.setText(entity.getTrainTypeShortName()+" "+entity.getTrainNo());
            }
            if(entity.getRepairClassName()!=null){
                holder1.repair.setText(entity.getRepairClassName());
            }
            if(entity.getPlanBeginTimeTra()!=null){
                holder1.time.setText(entity.getPlanBeginTimeTra());
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

     class ReceivedTrainHolder extends RecyclerView.ViewHolder {
        ReceivedTrain model;
        public TextView train;
        public TextView repair;
        public TextView time;

        public ReceivedTrainHolder(View itemView) {
            super(itemView);
            train = (TextView) itemView.findViewById(R.id.receive_train_item_train);
            repair = (TextView) itemView.findViewById(R.id.receive_train_item_repair);
            time = (TextView) itemView.findViewById(R.id.receive_train_item_time);
        }
         public TextView getTrain() {
             return train;
         }

         public TextView getRepair() {
             return repair;
         }

         public TextView getTime() {
             return time;
         }
    }
}
