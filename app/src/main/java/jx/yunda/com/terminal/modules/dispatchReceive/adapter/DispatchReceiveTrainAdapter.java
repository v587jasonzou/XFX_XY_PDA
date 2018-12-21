package jx.yunda.com.terminal.modules.dispatchReceive.adapter;

import android.view.View;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.dispatchReceive.model.DispatchReceiveTrain;
import jx.yunda.com.terminal.utils.LogUtil;

public class DispatchReceiveTrainAdapter extends BaseRecyclerAdapter<DispatchReceiveTrain, DispatchReceiveTrainAdapter.DispatchReceiveTrainHolder> {

    @Override
    protected int getLayoutId() {
        return R.layout.dispatch_receive_train_item;
    }

    @Override
    protected DispatchReceiveTrainHolder createViewHolder(View view) {
        return null;
    }

    public static class DispatchReceiveTrainHolder extends BaseRecyclerAdapter.BaseViewHolder<DispatchReceiveTrain> {

        DispatchReceiveTrain train;
        public DispatchReceiveTrainHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(DispatchReceiveTrain data) {
            try {
                train = data;

            } catch (Exception ex) {
                LogUtil.e("交车申请列表显示", ex.toString());
            }
        }
    }
}
