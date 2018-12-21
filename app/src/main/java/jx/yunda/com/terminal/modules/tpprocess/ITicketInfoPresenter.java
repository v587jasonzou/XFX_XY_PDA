package jx.yunda.com.terminal.modules.tpprocess;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ITicketInfoPresenter extends BasePresenter<ITicketInfo> {
    public static int TYPE_MAJOR = 1;//专业
    public static int TYPE_EMPLOYEE = 2;//考核
    public ITicketInfoPresenter(ITicketInfo view) {
        super(view);
    }
    public void getFalutInfo(final int position ,String str){
        RequestFactory.getInstance().createApi(TicketApi.class).getFaultInfos(str).enqueue(new Callback<List<FaultTreeBean>>() {
            @Override
            public void onResponse(Call<List<FaultTreeBean>> call, Response<List<FaultTreeBean>> response) {
//                Log.e("name",response.body().get(0).getName());
                List<FaultTreeBean> faultBeans = response.body();
                if(faultBeans!=null&&faultBeans.size()>0){
                    getViewRef().OnLoadEquipSuccess(position,response.body());
                }else {
                    getViewRef().OnLoadFail("未获取到部件信息");
                }

            }

            @Override
            public void onFailure(Call<List<FaultTreeBean>> call, Throwable t) {
                getViewRef().OnLoadFail("获取部件信息失败:"+t.getMessage());
            }
        });
    }
    public void getLable(final int type){
        req(RequestFactory.getInstance().createApi(TicketApi.class).getTicketType("JXGC_Fault_Ticket_YYFX"), new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                Log.e("json",result);
                BaseBean<List<TicketTypeBean>> bean = JSON.parseObject(result,new TypeReference<BaseBean<List<TicketTypeBean>>>(){});
                if(bean!=null){
                    if(bean.isSuccess()){
                        if(TYPE_MAJOR==type){
                            if(bean.getRoot()!=null&&bean.getRoot().size()>0){
                                List<TicketTypeBean> majors = new ArrayList<>();
                                TicketTypeBean beans = null;
                                for(int i = 0;i<bean.getRoot().size();i++){
                                    if("专业".equals(bean.getRoot().get(i).getDictname())){
                                        beans = bean.getRoot().get(i);
                                        break;
                                    }
                                }
                                for(int i =0;i<bean.getRoot().size();i++){
                                    if(bean.getRoot().get(i).getDictid().length()>2&&
                                            bean.getRoot().get(i).getDictid().substring(0,2).equals(beans.getDictid())){
                                        majors.add(bean.getRoot().get(i));
                                    }
                                }
                                getViewRef().OnLoadMajorSuccess(majors);
                            }

                        }else {
                            if(bean.getRoot()!=null&&bean.getRoot().size()>0){
                                List<TicketTypeBean> majors = new ArrayList<>();
                                TicketTypeBean beans = null;
                                for(int i = 0;i<bean.getRoot().size();i++){
                                    if("考核".equals(bean.getRoot().get(i).getDictname())){
                                        beans = bean.getRoot().get(i);
                                        break;
                                    }
                                }
                                for(int i =0;i<bean.getRoot().size();i++){
                                    if(bean.getRoot().get(i).getDictid().length()>2&&
                                            bean.getRoot().get(i).getDictid().substring(0,2).equals(beans.getDictid())){
                                        majors.add(bean.getRoot().get(i));
                                    }
                                }
                                getViewRef().OnLoadEmployeeSuccess(majors);
                            }

                        }

                    }else {
                        if(TYPE_MAJOR==type){
                            getViewRef().OnLoadFail("获取专业数据失败，请重试");
                        }else {
                            getViewRef().OnLoadFail("获取考核数据失败，请重试");
                        }

                    }
                }else {
                    getViewRef().OnLoadFail("连接服务器失败，请重试");
                }

            }

            @Override
            public void onError(ApiException e) {
                Log.e("错误",e.getMessage());
//                getViewRef().onLoadTicktFail(e.getMessage());
            }
        });
    }
    public void getDict(String idx, String entityJson, final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).getDict(idx,entityJson)
                .enqueue(new Callback<List<DictBean>>() {
                    @Override
                    public void onResponse(Call<List<DictBean>> call, Response<List<DictBean>> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().OnDicLoadSuccess(response.body(),position);
                        }else {
                            getViewRef().OnDicLoadSuccess(null,position);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DictBean>> call, Throwable t) {
                        getViewRef().OnDicLoadFaild("获取标签信息失败"+t.getMessage());
                    }
                });
    }
    public void getFaultList(String entityJson){
        RequestFactory.getInstance().createApi(TicketApi.class).getFaultList(entityJson).enqueue(new Callback<List<FaultBean>>() {
            @Override
            public void onResponse(Call<List<FaultBean>> call, Response<List<FaultBean>> response) {
                if(response.body()!=null&&response.body().size()>0){
                    getViewRef().OnLoadListSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<FaultBean>> call, Throwable t) {
                getViewRef().OnLoadFail("获取故障现象联想失败"+t.getMessage());
            }
        });

    }
    public int position1,imageNo1;
    public void UpLoadImages(String Base64,String extname,int position, int imageNo){
        this.position1 = position;
        this.imageNo1 = imageNo;
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadImage(Base64,extname).enqueue(new Callback<ImageResponsBean>() {
            @Override
            public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                ImageResponsBean bean = response.body();
                if(bean!=null&&bean.isSuccess()){
                    getViewRef().OnUpLoadImagesSuccess(bean.getFilePath(),position1,imageNo1);
                }else {
                    getViewRef().OnLoadFail("上传图片失败",position1,imageNo1);
                }
            }

            @Override
            public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                getViewRef().OnLoadFail("上传图片失败"+t.getMessage(),position1,imageNo1);
            }
        });
    }

    public void SaveTicket(long operatorid,String jsonData,String faultPhotos){
        RequestFactory.getInstance().createApi(TicketApi.class).saveTicket(operatorid,jsonData,faultPhotos).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null){
                    if(bean.isSuccess()){
                        getViewRef().OnSubmitSuccess();
                    }else {
                        getViewRef().OnSubmitFaild("提交失败"+bean.getErrMsg());
                    }
                }else {
                    getViewRef().OnSubmitFaild("提交失败");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnSubmitFaild("提交失败"+t.getMessage());
            }
        });
    }

    /**处理选中的【专业】,【类型】...
     * @param dictBeanList
     * @param map
     */
    public Map<String,Object> assembleSelectedDict(List<DictBean> dictBeanList, Map<String,Object> map) {
        if (map==null)
            return map;
        /*处理选中的子节点*/
        if (dictBeanList != null && dictBeanList.size() > 0) {
            List<DictBean> temps = new ArrayList<>();
            for (DictBean bean : dictBeanList) {/*遍历父节点*/
                if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                    for (DictBean child : bean.getChildList()) {/*遍历子节点*/
                        if (child.isChecked()) {
                            temps.add(child);
                        }
                    }
                }
            }
            if (temps.size() > 0) {
                String str = "";
                String strID = "";
                for (int i = 0; i < temps.size(); i++) {
                    if (i == 0) {
                        str = str + temps.get(i).getName();
                        strID = strID + temps.get(i).getId();
                    } else {
                        str = str + ";" + temps.get(i).getName();
                        strID = strID + ";" + temps.get(i).getId();
                    }
                }
                /*选中的子节点(不包含父节点)*/
                map.put("reasonAnalysisID", strID);
                map.put("reasonAnalysis", str);
            }
        }
        return map;
    }

    public void SaveTicketManage(long operatorid,String jsonData,String faultPhotos,String skillList){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitTicketManage(operatorid,jsonData,faultPhotos,skillList).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null){
                    if(bean.isSuccess()){
                        getViewRef().OnSubmitSuccess();
                    }else {
                        getViewRef().OnLoadFail(bean.getErrMsg(),-1,-1);
                    }
                }else {
                    getViewRef().OnLoadFail("提交失败",-1,-1);
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFail("提交失败"+t.getMessage(),-1,-1);
            }
        });
    }

    public void SaveTicketManageOther(long operatorid,String jsonData,String faultPhotos){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitTicketManageOther(operatorid,jsonData,faultPhotos).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null){
                    if(bean.isSuccess()){
                        getViewRef().OnSubmitSuccess();
                    }else {
                        getViewRef().OnLoadFail(bean.getErrMsg(),-1,-1);
                    }
                }else {
                    getViewRef().OnLoadFail("提交失败",-1,-1);
                }

            }
            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFail("提交失败"+t.getMessage(),-1,-1);
            }
        });
    }
    public void getStandard(String idx){
        RequestFactory.getInstance().createApi(TicketApi.class).getStandard(idx)
                .enqueue(new Callback<BaseBean<List<StandardBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<StandardBean>>> call, Response<BaseBean<List<StandardBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().OnLoadStandardSuccess(response.body().getRoot());
                            }else {
                                getViewRef().OnLoadFail("未获取到相关检修标准信息，请重试");
                            }
                        }else {
                            getViewRef().OnLoadFail("未获取到相关检修标准信息，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<StandardBean>>> call, Throwable t) {
                        getViewRef().OnLoadFail("未获取到相关检修标准信息，请重试"+t.getMessage());
                    }
                });
    }
    public void getImages(String attachmentKeyIDX, final String node){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,node).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<FileBaseBean> images = new ArrayList<>();
                if(bean.getFileUrlList()!=null){
                    images.addAll(bean.getFileUrlList());
                }
                if(node.equals("nodeTpAtt")){
                    getViewRef().OnGetImagesSuccess(images);
                }else {
                    getViewRef().OnGetImagesXpSuccess(images);
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFail("获取故障现象联想失败"+t.getMessage());
            }
        });

    }
    public void UpLevel(String idx,String target,String orgnial){
        RequestFactory.getInstance().createApi(TicketApi.class).LevelUpTicket(idx,target,orgnial).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if (response.body().isSuccess()){
                        getViewRef().OnBackSuccess();
                    }else {
                        getViewRef().OnLoadFail("退回失败，请重试");
                    }
                }else {
                    getViewRef().OnLoadFail("退回失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFail("退回失败，请重试"+t.getMessage());
            }
        });
    }

    public String[] getSelectedDicts(String dicts){
        String[] ids = new String[0];
        if (dicts!=null && dicts.contains(";")) {
            ids = dicts.split(";");
        }
        return ids;
    }

    /**
     * @param dicts
     * @param selectedDicts 形如:{1020;2030;...}
     */
    public void initDictsWithSelected(List<DictBean> dicts,String selectedDicts){
        if (selectedDicts==null||selectedDicts.length()==0) {
            return;
        }
        String[] ids = getSelectedDicts(selectedDicts);
        for (String id : ids) {
            for (DictBean dict : dicts) {
                if (dict.getId().equals(id)) {
                    dict.setChecked(true);
                    break;
                }
            }
        }


    }

}
