package jx.yunda.com.terminal.modules.pullTrainNotice.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jakewharton.rxbinding.view.RxView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;
import jx.yunda.com.terminal.modules.pullTrainNotice.presenter.PullTrainNoticeDetailPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.functions.Action1;

public class PullTrainNoticeDetailAdapter extends BaseRecyclerAdapter<PullTrainNoticeItem, PullTrainNoticeDetailAdapter.PullTrainNoticeTrainHolder> {
    PullTrainNoticeDetailPresenter mPresenter;

    public PullTrainNoticeDetailAdapter(PullTrainNoticeDetailPresenter p) {
        super();
        mPresenter = p;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pull_train_notice_detail_item;
    }

    @Override
    protected PullTrainNoticeDetailAdapter.PullTrainNoticeTrainHolder createViewHolder(View view) {
        return new PullTrainNoticeDetailAdapter.PullTrainNoticeTrainHolder(view);
    }

    @Override
    public void onBindViewHolder(final PullTrainNoticeTrainHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        RxView.clicks(holder.delBtn)
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mPresenter == null) return;
                        mPresenter.deletePullNotice(holder.model.getIdx(), new HttpOnNextListener() {
                            public void onNext(String result, String mothead) {
                                BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                                });
                                if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                                    ToastUtil.toastShort("删除成功！");
                                    getData().remove(position);
                                    notifyDataSetChanged();
                                } else
                                    ToastUtil.toastShort(rst.getErrMsg());
                            }

                            @Override
                            public void onError(ApiException e) {
                                ToastUtil.toastShort(e.getMessage());
                                LogUtil.e("牵车通知详情页面，删除未确认的详情", e.toString());
                            }
                        });
                    }
                });
    }

    public static class PullTrainNoticeTrainHolder extends BaseRecyclerAdapter.BaseViewHolder<PullTrainNoticeItem> {
        PullTrainNoticeItem model;

        @BindView(R.id.pull_train_notice_detail_item_position)
        TextView position;
        @BindView(R.id.pull_train_notice_detail_item_affirm_time)
        TextView time;
        @BindView(R.id.pull_train_notice_detail_item_affirm_person)
        TextView affirmPerson;
        @BindView(R.id.pull_train_notice_detail_item_del)
        Button delBtn;

        public PullTrainNoticeTrainHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(PullTrainNoticeItem data) {
            try {
                model = data;
                String p = "";
                p = TextUtils.isEmpty(data.getHomePosition()) ? "" : data.getHomePosition();
                p += TextUtils.isEmpty(data.getDestination()) ? "" : (" 至 " + data.getDestination());
                position.setText(p);
                time.setText(data.getConfirmTime());
                affirmPerson.setText(data.getConfirmPersonName());
                affirmPerson.setVisibility("1".equals(data.getConfirmStatus()) ? View.VISIBLE : View.GONE);
                delBtn.setVisibility(TextUtils.isEmpty(data.getConfirmStatus()) || "0".equals(data.getConfirmStatus()) ? View.VISIBLE : View.GONE);
            } catch (Exception ex) {
                LogUtil.e("调车通知单添加页面机车列表item显示", ex.toString());
            }
        }
    }
}
