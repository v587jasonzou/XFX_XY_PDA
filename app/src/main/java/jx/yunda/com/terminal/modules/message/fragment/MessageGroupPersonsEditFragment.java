package jx.yunda.com.terminal.modules.message.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.promeg.pinyinhelper.Pinyin;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.message.MessageActivity;
import jx.yunda.com.terminal.modules.message.adapter.MessageGroupAllPersonAdapter;
import jx.yunda.com.terminal.modules.message.adapter.MessageGroupPersonAdapter;
import jx.yunda.com.terminal.modules.message.adapter.MessageGroupPersonSwipeMenuAdapter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.presenter.IMessageGroupPersonsEdit;
import jx.yunda.com.terminal.modules.message.presenter.MessageGroupPersonsEditPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * <li>说明：消息页面：群组内可发送的人员列表移除
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageGroupPersonsEditFragment extends BaseFragment<MessageGroupPersonsEditPresenter> implements IMessageGroupPersonsEdit {

    @BindView(R.id.message_group_person_edit_title)
    TextView tvTitle;
    @BindView(R.id.message_group_person_edit_total)
    TextView tvTotal;
    @BindView(R.id.message_group_person_edit_list)
    SwipeMenuListView smList;
    AlertDialog addPersonDialog;
    MessageGroupPersonSwipeMenuAdapter smAdapter;
    MessageGroup messageGroup;

    @Override
    protected MessageGroupPersonsEditPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new MessageGroupPersonsEditPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.message_group_persons_edit_fm;
    }

    @Override
    public void initSubViews(View view) {
        //设置SwipeMenuListView适配器
        smAdapter = new MessageGroupPersonSwipeMenuAdapter(getContext());
        smList.setAdapter(smAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(R.color.btn_remove);
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("移除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };
        smList.setMenuCreator(creator);
        smList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                boolean rst = true;
                try {
                    switch (index) {
                        case 0:
                            getPresenter().removeGroupPerson(messageGroup.getGroupId(), smAdapter.getItem(position).getEmpId(), position);
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {

                }
                return rst;
            }
        });
        setAddPersonDailog();
    }

    @Override
    public void initData() {
        try {
            messageGroup = ((MessageActivity) getActivity()).messageGroup;
            if (messageGroup == null) return;
            tvTitle.setText(messageGroup.getGroupName());
            getPresenter().getGroupPersons(messageGroup.getGroupId());
        } catch (Exception ex) {
            LogUtil.e("消息人员管理页面数据加载", ex.toString());
        }
    }

    @OnClick({R.id.message_group_person_edit_back, R.id.message_group_persons_edit_add})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.message_group_person_edit_back:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    break;
                case R.id.message_group_persons_edit_add:
                    //人员添加Todo
                    initAddPersonDialog();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("消息人员管理页面", ex.toString());
        }
    }

    public void pageNotifyDataSetChanged(List<MessageGroupPerson> messageGroupPersons) {
        try {
            smAdapter.setData(messageGroupPersons);
            smAdapter.notifyDataSetChanged();
            tvTotal.setText("群成员 共计" + messageGroupPersons.size() + "人");
        } catch (Exception e) {
            LogUtil.e("消息组人员编辑页面刷新", e.toString());
        }
    }

    public void pageNotifyAllPersonDataSetChanged(List<MessageGroupPerson> messageGroupPersons) {
        try {
            allPersonAdapter.setDataAll(messageGroupPersons);
            allPersonAdapter.setData(messageGroupPersons);
            allPersonAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtil.e("消息组人员编辑页面刷新", e.toString());
        }
    }

    @Override
    public void dismissAddPersonDialog() {
        if (addPersonDialog != null)
            addPersonDialog.dismiss();
    }

    @Override
    public void showAddPersonDialog() {
        if (addPersonDialog != null)
            addPersonDialog.show();
    }

    MessageGroupAllPersonAdapter allPersonAdapter;

    private void initAddPersonDialog() {
        getPresenter().searchAllPerson();
    }

    private void setAddPersonDailog() {
        if (addPersonDialog == null) {
            View view = View.inflate(getContext(), R.layout.message_select_person_dialog, null);
            final TextView tvSearch = (TextView) view.findViewById(R.id.msg_select_person_search);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.msg_select_person_recycler);
            TextView tvSelectedPersons = (TextView) view.findViewById(R.id.msg_select_person_persons);
            Button btnCancel = (Button) view.findViewById(R.id.msg_select_person_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.msg_select_person_confirm);
            allPersonAdapter = new MessageGroupAllPersonAdapter();
            rvList.setAdapter(allPersonAdapter);
            rvList.setLayoutManager(new LinearLayoutManager(view.getContext()));

            RxTextView.textChanges(tvSearch)
                    .debounce(1, TimeUnit.SECONDS)
                    .compose(this.<CharSequence>bindToLifecycle())
                    .skip(1)
                    .map(new Func1<CharSequence, String>() {
                        @Override
                        public String call(CharSequence charSequence) {
                            return charSequence.toString();
                        }
                    }).observeOn(Schedulers.io())
                    .map(new Func1<String, List<MessageGroupPerson>>() {
                        @Override
                        public List<MessageGroupPerson> call(String s) {
                            List<MessageGroupPerson> pList = new ArrayList<>();
                            if (s.toString().equals("请输入人员姓名或拼音") || "".equals(s.toString())) {
                                for (int i = 0; i < allPersonAdapter.getDataAll().size(); i++) {
                                    pList = allPersonAdapter.getDataAll();
                                }
                            } else {
                                String searchStr = s.toString().toUpperCase();
                                for (int i = 0; i < allPersonAdapter.getDataAll().size(); i++) {
                                    MessageGroupPerson p = allPersonAdapter.getDataAll().get(i);
                                    if (p.getNamePinyIn().contains(searchStr) || p.getEmpName().contains(searchStr))
                                        pList.add(p);
                                }
                            }
                            return pList;
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<MessageGroupPerson>>() {
                        @Override
                        public void call(List<MessageGroupPerson> persons) {
                            allPersonAdapter.setData(persons);
                            allPersonAdapter.notifyDataSetChanged();
                        }
                    });
            RxView.clicks(btnCancel)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            addPersonDialog.dismiss();
                        }
                    });
            RxView.clicks(btnConfirm)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            List<MessageGroupPerson> msgGroupPersons = allPersonAdapter.getDataAll();
                            List<Receiver> selectReceivers = new ArrayList<>();
                            Receiver r = null;
                            for (MessageGroupPerson person : msgGroupPersons) {
                                if (person.isChecked) {
                                    r = new Receiver();
                                    r.setEmpId(person.getEmpId());
                                    r.setEmpName(person.getEmpName());
                                    r.setEmpPost(person.getEmpPost());
                                    selectReceivers.add(r);
                                }
                            }
                            getPresenter().addGroupPerson(messageGroup.getGroupId(), selectReceivers);
                        }
                    });
            addPersonDialog = new AlertDialog.Builder(getContext()).setView(view).create();
            addPersonDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    tvSearch.setText("");
                }
            });
        }
    }
}