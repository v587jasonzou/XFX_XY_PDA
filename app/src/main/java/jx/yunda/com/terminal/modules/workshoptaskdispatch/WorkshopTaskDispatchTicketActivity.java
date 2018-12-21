package jx.yunda.com.terminal.modules.workshoptaskdispatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.IWorkshopTaskDispatchTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.WorkshopTaskDispatchTicketPresenter;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class WorkshopTaskDispatchTicketActivity extends BaseActivity<WorkshopTaskDispatchTicketPresenter>
        implements View.OnClickListener, IWorkshopTaskDispatchTicket {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;

    @BindView(R.id.tv_faultLocation)
    TextView tvFaultLocation;

    @BindView(R.id.tv_faultPhenomenon)
    TextView tvFaultPhenomenon;
    @BindView(R.id.tv_faultType)
    TextView tvFaultType;
    @BindView(R.id.tv_faultInfo)
    TextView tvFaultInfo;

    @BindView(R.id.gvImages)
    SodukuGridView gvImages;

    @BindView(R.id.tvResolution)
    EditText tvResolution;
    @BindView(R.id.tvDisposeClass)
    TextView tvDisposeClass;
    @BindView(R.id.tvOverFix)
    TextView tvOverFix;

    @BindView(R.id.btn_dispatch)
    Button btnDispatch;
    @BindView(R.id.ll_btn)
    RelativeLayout llBtn;

    FaultTicket ticketBean;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    ImagesAdapter adapter;

    @Override
    protected WorkshopTaskDispatchTicketPresenter getPresenter() {
        return new WorkshopTaskDispatchTicketPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_workshoptaskdispatch_ticket;
    }

    @Override
    public void initSubViews(View view) {
        //设置toolbar
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDispatch.setOnClickListener(this);
        tvDisposeClass.setOnClickListener(this);
        tvOverFix.setOnClickListener(this);

        adapter = new ImagesAdapter(this, images);
        adapter.setState("1");
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle1 = new Bundle();
                Intent intent = new Intent(WorkshopTaskDispatchTicketActivity.this, PhotoReadActivity.class);
                bundle1.putInt("position", position);
                bundle1.putString("state","1");
                bundle1.putSerializable("images", imagesTemp);
                intent.putExtras(bundle1);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            ticketBean = (FaultTicket) getIntent().getSerializableExtra("ticketBean");
            if (ticketBean != null) {
                tvFaultLocation.setText(ticketBean.getFixPlaceFullName());
                tvFaultPhenomenon.setText(ticketBean.getFaultDesc());
                tvFaultType.setText(ticketBean.getType());
                tvFaultInfo.setText(ticketBean.getTicketEmp() + "   " + DateUtil.date2String(ticketBean.getTicketTime(), "yyyy-MM-dd HH:mm"));

                if (!TextUtils.isEmpty(ticketBean.getResolutionContent())) {
                    tvResolution.setText(ticketBean.getResolutionContent());
                }

                if (!TextUtils.isEmpty(ticketBean.getRepairTeamName())) {
                    tvDisposeClass.setText(ticketBean.getRepairTeamName());
                    tvDisposeClass.setTag(ticketBean.getRepairTeam());
                }

                if (ticketBean.getOverRangeStatus() == null || FaultTicket.OVERRANGESTATUS_N == ticketBean.getOverRangeStatus()) {
                    tvOverFix.setText("否");
                    tvOverFix.setTag(FaultTicket.OVERRANGESTATUS_N);
                } else {
                    tvOverFix.setText("是");
                    tvOverFix.setTag(FaultTicket.OVERRANGESTATUS_Y);
                }

                // 判断是否填写数据
                if (TextUtils.isEmpty(tvResolution.getText().toString()) || TextUtils.isEmpty(tvDisposeClass.getText().toString())
                        || TextUtils.isEmpty(tvOverFix.getText().toString())) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }

                if (FaultTicket.STATUS_OPEN == ticketBean.getStatus()) {
                    llBtn.setVisibility(View.VISIBLE);
                } else {
                    llBtn.setVisibility(View.GONE);
                }

                mPresenter.getImages(ticketBean.getIdx(),"nodeTpAtt");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        if (ticketBean == null) return;

        switch (v.getId()) {
            case R.id.tvDisposeClass:
                mPresenter.getTicketClassList("");
                break;
            case R.id.tvOverFix:
                Map<Integer, String> overRangeN = new HashMap<>();
                overRangeN.put(FaultTicket.OVERRANGESTATUS_N, "否");
                Map<Integer, String> overRangeY = new HashMap<>();
                overRangeY.put(FaultTicket.OVERRANGESTATUS_Y, "是");
                final List<Map<Integer, String>> overRangeList = new ArrayList<>();
                overRangeList.add(overRangeN);
                overRangeList.add(overRangeY);
                OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        int key = overRangeList.get(options1).keySet().iterator().next();
                        tvOverFix.setText(overRangeList.get(options1).get(key));
                        tvOverFix.setTag(key);

                        // 判断是否填写数据
                        if (TextUtils.isEmpty(tvResolution.getText().toString()) || TextUtils.isEmpty(tvDisposeClass.getText().toString())
                                || TextUtils.isEmpty(tvOverFix.getText().toString())) {
                            btnDispatch.setEnabled(false);
                            btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                        } else {
                            btnDispatch.setEnabled(true);
                            btnDispatch.setBackgroundResource(R.drawable.button_selector);
                        }
                    }
                }).setTitleText("选择处理班组").setSubmitText("确定").build();
                List<String> list1 = new ArrayList<>();
                for (Map<Integer, String> map : overRangeList) {
                    list1.add(map.entrySet().iterator().next().getValue());
                }
                options.setPicker(list1);
                options.show();
                break;
            case R.id.btn_dispatch:
                // 判断是否填写数据
                if (TextUtils.isEmpty(tvResolution.getText().toString()) || TextUtils.isEmpty(tvDisposeClass.getText().toString())
                        || TextUtils.isEmpty(tvOverFix.getText().toString())) {
                    ToastUtil.toastShort("请选择作业班组或人员和作业台位！");
                    return;
                }

                ticketBean.setResolutionContent(tvResolution.getText().toString());
                ticketBean.setOverRangeStatus((Integer) tvOverFix.getTag());

                mPresenter.dispatchTicket(JSON.toJSONString(new String[]{ticketBean.getIdx()}), JSON.toJSONString(ticketBean));

                break;
        }
    }

    @Override
    public void onTicketClassListLoadSuccess(final List<OrgForWorkshopTaskDispatch> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvDisposeClass.setText(list.get(options1).getOrgname());
                tvDisposeClass.setTag(list.get(options1).getId());

                ticketBean.setRepairTeam(tvDisposeClass.getTag().toString());
                ticketBean.setRepairTeamName(tvDisposeClass.getText().toString());
                ticketBean.setRepairTeamOrgseq(list.get(options1).getOrgseq());

                // 判断是否填写数据
                if (TextUtils.isEmpty(tvResolution.getText().toString()) || TextUtils.isEmpty(tvDisposeClass.getText().toString())
                        || TextUtils.isEmpty(tvOverFix.getText().toString())) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }
            }
        }).setTitleText("选择处理班组").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (OrgForWorkshopTaskDispatch bean : list) {
            list1.add(bean.getOrgname());
        }
        options.setPicker(list1);
        options.show();
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onDispatchSuccess(String msg) {
        ToastUtil.toastSuccess();

        Intent intent = new Intent();
        intent.putExtra("dispatchSuccess", true);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onDispatchFail(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onGetImagesSuccess(List<FileBaseBean> images) {
        if (images.size() > 0) {
            this.images.clear();
            for (FileBaseBean str:images) {
                this.images.add(Utils.getImageUrl(str.getFileUrl()));
            }
            adapter.notifyDataSetChanged();
            imagesTemp.clear();
            if (images.size() > 0) {
                for (int i =0; i<images.size(); i++) {
                    ImageItem item = new ImageItem();
                    item.path = Utils.getImageUrl(images.get(i).getFileUrl());
                    imagesTemp.add(item);
                }
            }
        }
    }
}
