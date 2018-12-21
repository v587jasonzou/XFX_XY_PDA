package jx.yunda.com.terminal.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.List;


import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.adapter.SelectUserAdapter;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.utils.ToastUtil;

/**
 * 人员选择通用控件
 * 创建人：周雪巍
 * 时间：2018-07-30
 *
 * **/
@SuppressLint("AppCompatCustomView")
public class SelectUserTextView extends TextView {
    AlertDialog userDialog;
    List<UserSelectBean> users = new ArrayList<>();
    List<UserSelectBean> userlistTemp = new ArrayList<>();
    ListView lvUser;
    Button btCancle;
    Button btSubmit;
    Button btClear;
    TextView tvUsers;
    EditText etSearch;
    Context mContext;
    SelectUserAdapter adapter;
    OnSelectCompleteLisnter onSelectCompleteLisnter;
    public SelectUserTextView(Context context) {
        super(context);
        mContext = context;
    }
    public boolean IsEmpty(){
        if(userlistTemp.size()==0&&users.size()==0){
            return true;
        }else {
            return false;
        }
    }
    public SelectUserTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    public void setUsers(List<UserSelectBean> list){
        users.clear();
        userlistTemp.clear();
        userlistTemp.addAll(list);
        users.addAll(list);
        for(UserSelectBean bean : users){
            if(bean.getSelected()==null){
                bean.setSelected(false);
            }
        }
        for(UserSelectBean bean : userlistTemp){
            if(bean.getSelected()==null){
                bean.setSelected(false);
            }
        }
    }
    public void setSelectUsers(List<UserSelectBean> temps){
        if(temps!=null&&temps.size()>0){
            for(int i = 0; i <temps.size();i++){
                for(int j = 0; j< users.size();j++){
                    if(temps.get(i).getIdx().equals(users.get(j).getIdx())){
                        users.get(j).setSelected(true);
                        break;
                    }
                }
            }
        }
    }
    public List<UserSelectBean> getSelectUsers(){
        List<UserSelectBean> temps = new ArrayList<>();
        for(UserSelectBean bean:users){
            if(bean.getSelected()){
                temps.add(bean);
            }
        }
        return temps;
    }
    public interface OnSelectCompleteLisnter{
        void OnSelectUserComplet();
    }
    public void setOnSelectCompleteLisnter(OnSelectCompleteLisnter onSelectCompleteLisnter){
        this.onSelectCompleteLisnter = onSelectCompleteLisnter;
    }
    private void init() {
       this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.size()==0){
                    ToastUtil.toastShort("没有人员可以选择");
                }else {
                    if(userDialog==null){
                        View view = LinearLayout.inflate(mContext, R.layout.dialog_select_user, null);
                        lvUser = (ListView)view.findViewById(R.id.lvUser);
                        btCancle = (Button) view.findViewById(R.id.btCancle);
                        btSubmit = (Button)view.findViewById(R.id.btSubmit);
                        btClear = (Button)view.findViewById(R.id.btClear);
                        tvUsers = (TextView)view.findViewById(R.id.tvUsers);
                        etSearch = (EditText)view.findViewById(R.id.etSearch);
                        btClear.setVisibility(GONE);
                        etSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if(s.toString().equals("请输入人员姓名或拼音")||"".equals(s.toString())){
                                    users.clear();
                                    users.addAll(userlistTemp);
                                }else {
                                    List<UserSelectBean> temps = new ArrayList<>();
                                    String Search = s.toString().toUpperCase();
                                    if(userlistTemp.size()>0){
                                        for(int i = 0;i<userlistTemp.size();i++){
                                            if(Pinyin.toPinyin(userlistTemp.get(i).getUserName(),"").toUpperCase().contains(Search)||
                                                    userlistTemp.get(i).getUserName().contains(Search)){
                                                temps.add(userlistTemp.get(i));
                                            }
                                        }
                                    }
                                    users.clear();
                                    users.addAll(temps);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        btCancle.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDialog.dismiss();
                            }
                        });
                        btSubmit.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDialog.dismiss();
                                onSelectCompleteLisnter.OnSelectUserComplet();
                            }
                        });

                        userDialog = new AlertDialog.Builder(mContext).setView(view).create();
                        userDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                etSearch.setText("");
                            }
                        });
                        userDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                users.clear();
                                users.addAll(userlistTemp);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        adapter =new SelectUserAdapter(mContext, users);
                        lvUser.setAdapter(adapter);

                    }
                    userDialog.show();
                    userDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                }
            }
        });
    }
}
