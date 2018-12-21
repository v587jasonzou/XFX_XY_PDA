package jx.yunda.com.terminal.modules.pullTrainNotice.adapter;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.pullTrainNotice.PullTrainNoticeEditActivity;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNotice;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class PullTrainNoticeSelectTrainAdapter extends BaseRecyclerAdapter<PullTrainNotice, PullTrainNoticeSelectTrainAdapter.PullTrainNoticeTrainHolder> {

    PullTrainNoticeEditActivity mActivity;

    public PullTrainNoticeSelectTrainAdapter(PullTrainNoticeEditActivity mActivity) {
        super();
        this.mActivity = mActivity;
    }

    private List<PullTrainNotice> dataAll = new ArrayList<>();

    public List<PullTrainNotice> getDataAll() {
        return dataAll;
    }

    public void setDataAll(List<PullTrainNotice> dAll) {
        this.dataAll.clear();
        if (dAll != null)
            this.dataAll.addAll(dAll);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pull_train_notice_select_train_item;
    }

    @Override
    protected PullTrainNoticeTrainHolder createViewHolder(View view) {
        return new PullTrainNoticeTrainHolder(view);
    }

    @Override
    public void onBindViewHolder(final PullTrainNoticeTrainHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        RxView.clicks(holder.text).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mActivity.setPageBySelectPullTrainNotice(holder.model);
                        mActivity.dismissSelectTrainDialog();
                    }
                });
    }

    public static class PullTrainNoticeTrainHolder extends BaseRecyclerAdapter.BaseViewHolder<PullTrainNotice> {

        PullTrainNotice model;
        @BindView(R.id.pull_train_notice_select_train_item_text)
        TextView text;

        public PullTrainNoticeTrainHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(PullTrainNotice t) {
            try {
                model = t;
                text.setText(t.getTrainTypeShortName() + " " + t.getTrainNo());
            } catch (Exception ex) {
                LogUtil.e("添加调车通知页面，机车选择Item", ex.toString());
            }
        }
    }
}
