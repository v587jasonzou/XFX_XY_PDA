package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;

public class HegeItemAdapter extends RecyclerView.Adapter {
    List<InspectorOrderBean> mList;
    LayoutInflater inflater;
    Context mContext;
    OnItemClickListner onItemClickListner;
    public int nowposition = -1;
    public HegeItemAdapter(Context context, List<InspectorOrderBean> beans){
        inflater = LayoutInflater.from(context);
        mContext = context;
        mList = beans;
    }
    public interface OnItemClickListner{
        void OnItemClick(int position);
        void OnEditComplet(int position);
        void OnComplet(int position, String msg);
    }
    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
    public int getNowPositiong(){
        return nowposition;
    }
    //定义Viewholder
    class MyViewholder extends RecyclerView.ViewHolder  {
        public LinearLayout llHege,llShuju;
        public TextView tvHegeName,tvHegeDataResult,tvTitle;
        public EditText etData;
        private ImageView tvUnit;

        public MyViewholder(View root) {
            super(root);
            llHege = (LinearLayout) root.findViewById(R.id.llHege);
            llShuju = (LinearLayout) root.findViewById(R.id.llShuju);
            tvHegeName = (TextView) root.findViewById(R.id.tvHegeName);
            tvHegeDataResult = (TextView) root.findViewById(R.id.tvHegeDataResult);
            tvTitle = (TextView) root.findViewById(R.id.tvTitle);
            tvUnit = (ImageView) root.findViewById(R.id.tvUnit);
            etData = (EditText) root.findViewById(R.id.etData);

        }

        public LinearLayout getLlHege() {
            return llHege;
        }

        public LinearLayout getLlShuju() {
            return llShuju;
        }

        public TextView getTvHegeName() {
            return tvHegeName;
        }

        public TextView getTvHegeDataResult() {
            return tvHegeDataResult;
        }
        public TextView getTvTitle() {
            return tvTitle;
        }
        public ImageView getTvUnit() {
            return tvUnit;
        }
        public EditText getEtData() {
            return etData;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_workorder_data,parent,false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MyViewholder holder1 = (MyViewholder) holder;
        final InspectorOrderBean entry = mList.get(position);
        if (holder instanceof MyViewholder) {
            //1、为了避免TextWatcher在第2步被调用，提前将他移除。
            if (((MyViewholder) holder).etData.getTag() instanceof TextWatcher) {
                ((MyViewholder) holder).etData.removeTextChangedListener((TextWatcher) (((MyViewholder) holder).etData.getTag()));
            }

            // 第2步：移除TextWatcher之后，设置EditText的Text。
            ((MyViewholder) holder).etData.setText(entry.getResultName());

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }


                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }


                @Override
                public void afterTextChanged(Editable editable) {
                    nowposition = position;
                    if (TextUtils.isEmpty(editable.toString())) {
                        entry.setDetectResult("");
                    } else {
                        entry.setDetectResult(editable.toString());
                    }
                }
            };
            ((MyViewholder) holder).etData.addTextChangedListener(watcher);
            ((MyViewholder) holder).etData.setTag(watcher);
            ((MyViewholder) holder).etData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        if(nowposition!=-1){
                            if (nowposition!=position){
                                onItemClickListner.OnEditComplet(nowposition);
                            }
                        }
                    }
                }
            });
        }
        if(entry!=null){
            holder1.tvTitle.setText(entry.getDetectItemContent());
            if(entry.getDetectResult()!=null){
                holder1.etData.setText(entry.getDetectResult());
            }else {
                if(entry.getResultName()!=null){
                    holder1.etData.setText(entry.getResultName());
                }
            }

            if(entry.getItemResultCount()>0){

                holder1.tvUnit.setVisibility(View.VISIBLE);
                holder1.tvUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListner.OnItemClick(position);
                    }
                });
            }else {
                holder1.tvUnit.setVisibility(View.GONE);
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
