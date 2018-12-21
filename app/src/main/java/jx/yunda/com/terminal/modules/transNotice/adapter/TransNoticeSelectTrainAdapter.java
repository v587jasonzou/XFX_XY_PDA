package jx.yunda.com.terminal.modules.transNotice.adapter;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.transNotice.model.Train;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class TransNoticeSelectTrainAdapter extends BaseRecyclerAdapter<Train, TransNoticeSelectTrainAdapter.TransNoticeTrainHolder> {


    private List<Train> dataAll = new ArrayList<>();

    public List<Train> getDataAll() {
        return dataAll;
    }

    public void setDataAll(List<Train> dAll) {
        this.dataAll.clear();
        if (dAll != null)
            this.dataAll.addAll(dAll);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.trans_notice_select_train_item;
    }

    @Override
    protected TransNoticeTrainHolder createViewHolder(View view) {
        return new TransNoticeTrainHolder(view);
    }

    @Override
    public void onBindViewHolder(final TransNoticeTrainHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.text.setBackgroundResource(R.drawable.blue_line_white_bg);
        RxView.clicks(holder.text).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getData().get(position).isChecked = !getData().get(position).isChecked;
                        holder.text.setBackgroundResource(getData().get(position).isChecked ? R.drawable.blue_line_active_bg : R.drawable.blue_line_white_bg);
                    }
                });
    }

    public static class TransNoticeTrainHolder extends BaseRecyclerAdapter.BaseViewHolder<Train> {

        Train model;
        @BindView(R.id.trans_notice_select_train_item_text)
        TextView text;

        public TransNoticeTrainHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(Train t) {
            try {
                model = t;
                text.setText(t.getTrainTypeShortName() + " " + t.getTrainNo());
            } catch (Exception ex) {
                LogUtil.e("添加调车通知页面，机车选择Item", ex.toString());
            }
        }
    }
}
