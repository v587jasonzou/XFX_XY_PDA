package jx.yunda.com.terminal.modules.receiveTrain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;
import jx.yunda.com.terminal.utils.LogUtil;

public class ReceivedTrainNoticeAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<ReceiveTrainNotice> mList;
    OnReceivedClickListner onReceivedClickListner;
    public ReceivedTrainNoticeAdapter(Context context, List<ReceiveTrainNotice> ReceivedTrain){
        mContext = context;
        mList = ReceivedTrain;
    }
    public interface OnReceivedClickListner{
        void OnReceiveClick(int position);
    }
    public void setOnOnReceivedClickListner(OnReceivedClickListner onReceivedClickListner){
        this.onReceivedClickListner = onReceivedClickListner;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.receive_train_notice_item,parent,false);
        ReceivedTrainNoticeAdapter.ReceivedTrainHolder viewholder = new ReceivedTrainNoticeAdapter.ReceivedTrainHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ReceiveTrainNotice entity = null;
        if(mList!=null&&mList.size()>0){
            entity = mList.get(position);
        }
        ReceivedTrainNoticeAdapter.ReceivedTrainHolder holder1 = (ReceivedTrainNoticeAdapter.ReceivedTrainHolder) holder;
        if(entity!=null){
            if(entity.getTrainTypeShortName()!=null&&entity.getTrainNo()!=null){
                holder1.train.setText(entity.getTrainTypeShortName()+" "+entity.getTrainNo());
            }
            if(entity.getRepairClassName()!=null){
                ((ReceivedTrainHolder) holder).repair.setText(entity.getRepairClassName());
            }
//            if(entity.get()!=null){
//                holder1.repair.setText(entity.getRepairClassName());
//            }
//            if(entity.getConfirmTime()!=null){
//                holder1.notice.setText(entity.getConfirmTime());
//            }
            holder1.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReceivedClickListner.OnReceiveClick(position);
                }
            });
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
        TextView train;
        TextView repair;
        TextView notice;
        Button submit;

        public ReceivedTrainHolder(View itemView) {
            super(itemView);
            train = (TextView) itemView.findViewById(R.id.receive_train_notice_item_train);
            repair = (TextView) itemView.findViewById(R.id.receive_train_notice_item_repair);
            notice = (TextView) itemView.findViewById(R.id.receive_train_notice_item_notice);
            submit = (Button) itemView.findViewById(R.id.receive_train_notice_item_submit);
        }
        public TextView getTrain() {
            return train;
        }

        public TextView getRepair() {
            return repair;
        }

        public TextView getNotice() {
            return notice;
        }

        public Button getSubmit(){
            return submit;
        }
    }

}
