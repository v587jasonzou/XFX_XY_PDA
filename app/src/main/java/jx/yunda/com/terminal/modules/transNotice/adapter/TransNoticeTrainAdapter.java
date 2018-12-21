package jx.yunda.com.terminal.modules.transNotice.adapter;

import android.opengl.Visibility;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jakewharton.rxbinding.view.RxView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.transNotice.fragment.TransNoticeEditFragment;
import jx.yunda.com.terminal.modules.transNotice.model.TransNoticeTrain;
import jx.yunda.com.terminal.modules.transNotice.model.Train;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.functions.Action1;

public class TransNoticeTrainAdapter extends BaseRecyclerAdapter<TransNoticeTrain, TransNoticeTrainAdapter.TransNoticeTrainHolder> {
    TransNoticeEditFragment fragment;
    boolean isSubmitNotice;

    public boolean isSubmitNotice() {
        return isSubmitNotice;
    }

    public void setSubmitNotice(boolean submitNotice) {
        isSubmitNotice = submitNotice;
    }

    public TransNoticeTrainAdapter(TransNoticeEditFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.trans_notice_edit_item;
    }

    @Override
    protected TransNoticeTrainAdapter.TransNoticeTrainHolder createViewHolder(View view) {
        return new TransNoticeTrainAdapter.TransNoticeTrainHolder(view);
    }

    @Override
    public void onBindViewHolder(final TransNoticeTrainHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.remove.setVisibility(isSubmitNotice ? View.GONE : View.VISIBLE);
        RxView.clicks(holder.startPosition)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        fragment.setOptionsPickerView(holder.startPosition, holder.endPosition, getData().get(position));
                    }
                });
        RxView.clicks(holder.endPosition)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        fragment.setOptionsPickerView(holder.startPosition, holder.endPosition, getData().get(position));
                    }
                });

        RxView.clicks(holder.remove)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        final TransNoticeTrain snTrain = getData().get(position);
                        if (TextUtils.isEmpty(snTrain.getIdx())) {
                            removeTrain(snTrain, position);
                        } else {
                            fragment.getPresenter().deleteNoticeTrainDetails(snTrain.getIdx(), new HttpOnNextListener() {
                                @Override
                                public void onNext(String result, String mothead) {
                                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                                    });
                                    if (rst != null && rst.getSuccess() != null && rst.getSuccess())
                                        removeTrain(snTrain, position);
                                    else
                                        ToastUtil.toastShort("机车移除失败！" + rst.getErrMsg());
                                }

                                @Override
                                public void onError(ApiException e) {
                                    ToastUtil.toastShort("机车移除失败！" + e.getMessage());
                                    LogUtil.e("机车移除失败！", e.toString());
                                }
                            });
                        }

                    }
                });
    }

    void removeTrain(TransNoticeTrain snTrain, int position) {
        List<TransNoticeTrain> trains = fragment.getSelectTransNoticeTrains();
        if (trains != null && trains.size() > 0) {
            for (TransNoticeTrain t : trains) {
                if ((snTrain.getTrainTypeShortName() + snTrain.getTrainNo()).equals(t.getTrainTypeShortName() + t.getTrainNo())) {
                    trains.remove(t);
                    break;
                }
            }
        }
        getData().remove(position);
        notifyDataSetChanged();
    }

    public static class TransNoticeTrainHolder extends BaseRecyclerAdapter.BaseViewHolder<TransNoticeTrain> {
        TransNoticeTrain model;

        @BindView(R.id.trans_notice_edit_item_train)
        TextView trainInfo;
        @BindView(R.id.trans_notice_edit_item_remove)
        ImageButton remove;
        @BindView(R.id.trans_notice_edit_item_start_position)
        EditText startPosition;
        @BindView(R.id.trans_notice_edit_item_end_position)
        EditText endPosition;

        public TransNoticeTrainHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(TransNoticeTrain data) {
            try {
                model = data;
                trainInfo.setText(model.getTrainTypeShortName() + " " + model.getTrainNo() + "【" + model.getdName() + "】");
                startPosition.setText(data.getHomePosition());
                endPosition.setText(data.getDestination());
            } catch (Exception ex) {
                LogUtil.e("调车通知单添加页面机车列表item显示", ex.toString());
            }
        }
    }
}
