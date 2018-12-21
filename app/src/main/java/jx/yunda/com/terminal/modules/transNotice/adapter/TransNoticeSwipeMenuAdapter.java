package jx.yunda.com.terminal.modules.transNotice.adapter;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.transNotice.TransNoticeActivity;
import jx.yunda.com.terminal.modules.transNotice.fragment.TransNoticeEditFragment;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TransNoticeSwipeMenuAdapter extends BaseAdapter {
    TransNoticeActivity mActivity;
    private List<TransNotice> data;
    TransNoticeSwipeMenuAdapter.ViewHolder holder;

    public TransNoticeSwipeMenuAdapter(TransNoticeActivity activity) {
        super();
        this.mActivity = activity;
        this.data = new ArrayList<TransNotice>();
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public TransNotice getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<TransNotice> getData() {
        return data;
    }

    public void setData(List<TransNotice> data) {
        this.data.clear();
        if (data != null)
            this.data.addAll(data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        try {
            final TransNotice sNotice = getItem(position);
            if (convertView == null) {
                final TransNoticeActivity mainActivity = mActivity;
                convertView = LayoutInflater.from(mainActivity).inflate(R.layout.trans_notice_item, viewGroup, false);
                holder = new TransNoticeSwipeMenuAdapter.ViewHolder();
                holder.setProperty(convertView);
                convertView.setTag(holder);
                RxView.clicks(holder.noticeName).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                mainActivity.selectTransNotice = getItem(position);
                                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                TransNoticeEditFragment snAddFragment = new TransNoticeEditFragment();
                                snAddFragment.initPage(mainActivity.msgGroupId, mainActivity.selectTransNotice);
                                transaction.add(R.id.trans_notice_panel, snAddFragment);
                                transaction.commit();
                            }
                        });
            } else
                holder = (TransNoticeSwipeMenuAdapter.ViewHolder) convertView.getTag();
            holder.setPageContent(sNotice);
        } catch (Exception ex) {
        }
        return convertView;
    }

    //    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
    @Override
    public int getItemViewType(int position) {
//        TransNotice model = getItem(position);
//        int type = "1".equals(model.getSubmitStatus()) ? 1 : 0;
        return position;
    }


    class ViewHolder {
        TransNotice model;
        TextView noticeName;
        TextView noticeComplete;
        TextView noticeStatus;

        void setProperty(View view) {
            if (view == null)
                return;
            noticeName = (TextView) view.findViewById(R.id.trans_notice_item_name);
            noticeComplete = (TextView) view.findViewById(R.id.trans_notice_item_complete);
            noticeStatus = (TextView) view.findViewById(R.id.trans_notice_item_status);
        }

        void setPageContent(TransNotice data) {
            model = data;
            model.setConfirmCount(TextUtils.isEmpty(model.getConfirmCount()) ? "0" : model.getConfirmCount());
            model.setTotal(TextUtils.isEmpty(model.getTotal()) ? "0" : model.getTotal());
            noticeName.setText(model.getNoticeName());
            noticeComplete.setText(model.getConfirmCount() + "/" + model.getTotal());
            noticeStatus.setText("1".equals(model.getSubmitStatus()) ? "已提交" : "未提交");
        }
    }
}
