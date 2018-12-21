package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardChildBean;


public class StandardAdapter extends BaseExpandableListAdapter {
    List<StandardBean> parrents;
    Context context;
    MyOnItemclickListner myOnItemclickListner;
    public interface MyOnItemclickListner{
        void OnParentClick(int parentPosition, boolean isExpand);
        void OnChildClick(int parentPosition, int ChildPosision);
    }
    public void SetMyClickListner(MyOnItemclickListner myOnItemclickListner){
        this.myOnItemclickListner = myOnItemclickListner;
    }
    public StandardAdapter(Context context, List<StandardBean> parrents) {
        this.context = context;
        this.parrents = parrents;
    }

    @Override
    public int getGroupCount() {
        if (parrents != null) {
            return parrents.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<StandardChildBean> chiditems = parrents.get(groupPosition).getContentList();
        if (chiditems != null) {
            return chiditems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parrents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<StandardChildBean> chiditems = parrents.get(groupPosition).getContentList();
        return chiditems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_standard_list, null);
        } else {
            view = convertView;
        }

        TextView tvStandardName = (TextView) view.findViewById(R.id.tvStandardName);
        StandardBean entity = parrents.get(groupPosition);

        if (entity != null) {
            if(entity.getStepName()!=null){
                tvStandardName.setText(entity.getStepName());
            }else {
                tvStandardName.setText("");
            }
        }

        tvStandardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnParentClick(groupPosition,isExpanded);
            }
        });
//        if(isExpanded){
//            if (cardView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
//                p.setMargins(SizeUtils.dp2px(16), SizeUtils.dp2px(8), SizeUtils.dp2px(16),0);
//                cardView.requestLayout();
//            }
//            ivArrow.setImageResource(R.mipmap.item_arrow_up);
//        }else {
//            ivArrow.setImageResource(R.mipmap.item_arrow_down);
//            if (cardView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
//                p.setMargins(SizeUtils.dp2px(16), SizeUtils.dp2px(8), SizeUtils.dp2px(16), SizeUtils.dp2px(8));
//                cardView.requestLayout();
//            }
//        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_standard_child_list, null);
        } else {
            view = convertView;
        }
        TextView tvStandardChildName = (TextView) view.findViewById(R.id.tvStandardChildName);
        TextView etContent = (TextView) view.findViewById(R.id.etContent);
        ImageView ivEdit = (ImageView)view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnChildClick(groupPosition,childPosition);
            }
        });
//        if(childPosition==parrents.get(groupPosition).getWorkOrders().size()-1){
//            if (cvCard.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cvCard.getLayoutParams();
//                p.setMargins(SizeUtils.dp2px(16), 0, SizeUtils.dp2px(16), SizeUtils.dp2px(8));
//                cvCard.requestLayout();
//            }
//        }else {
//            if (cvCard.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cvCard.getLayoutParams();
//                p.setMargins(SizeUtils.dp2px(16), 0, SizeUtils.dp2px(16),0);
//                cvCard.requestLayout();
//            }
//        }
//        cvCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myOnItemclickListner.OnChildClick(groupPosition,childPosition);
//            }
//        });
//        cbCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myOnItemclickListner.OnChildClick(groupPosition,childPosition);
//            }
//        });
//        llConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myOnItemclickListner.OnConfirmClick(groupPosition,childPosition);
//            }
//        });
//        llManageOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myOnItemclickListner.OnManageClick(groupPosition,childPosition);
//            }
//        });
        StandardChildBean entity = parrents.get(groupPosition).getContentList().get(childPosition);
        if (entity != null) {
//            if (entity.getColor() != null && !"".equals(entity.getColor())) {
//                tvInfo.setBackgroundColor(Color.parseColor(entity.getColor()));
//                tvInfo.setTextColor(Color.parseColor("#ffffff"));
//            } else {
//                tvInfo.setBackgroundColor(Color.parseColor("#00000000"));
//                tvInfo.setTextColor(Color.parseColor("#603b07"));
//            }
            if(entity.getContent()!=null){
                tvStandardChildName.setText(entity.getContent());
            }else {
                tvStandardChildName.setText("");
            }
            if(entity.getInfo()!=null){
                etContent.setText(entity.getInfo());
            }else {
                etContent.setText("");
            }
        }
//        etContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s!=null){
//                    entity.setInfo(s.toString());
//                }else {
//                    entity.setInfo("");
//                }
//            }
//        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
