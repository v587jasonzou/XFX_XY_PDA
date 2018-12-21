package jx.yunda.com.terminal.base.baseDictData.presenter;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.base.baseDictData.api.BaseDictApi;
import jx.yunda.com.terminal.utils.LogUtil;

public class BaseDictPresenter extends BasePresenter {
    public BaseDictPresenter(Object view) {
        super(view);
    }

    //获取提票专业及考核类别
    public void getDicDataBy_JXGC_Fault_Ticket_YYFX(HttpOnNextListener listener) {
        req(RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictList("JXGC_Fault_Ticket_YYFX"), listener);
    }

    public void getDictForBQ(String idx, HttpOnNextListener listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid", "JXGC_Fault_Ticket_YYFX");
        req(RequestFactory.getInstance().createApi(BaseDictApi.class).getDictForBQ(idx, JSON.toJSONString(map)), listener);
//        RequestFactory.getInstance().createApi(BaseDictApi.class).getDictForBQ(idx,entityJson)
//                .enqueue(new Callback<List<DictBean>>() {
//                    @Override
//                    public void onResponse(Call<List<DictBean>> call, Response<List<DictBean>> response) {
//                        if(response!=null&&response.body()!=null){
//                            getViewRef().OnDicLoadSuccess(response.body(),position);
//                        }else {
//                            getViewRef().OnDicLoadSuccess(null,position);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<DictBean>> call, Throwable t) {
//                        getViewRef().OnDicLoadFaild("获取标签信息失败"+t.getMessage());
//                    }
//                });
    }


    //获取调车通知单起始位置
    public void getDicDataForTransTrainStartPosition(boolean isShowProcess, HttpOnNextListener listener) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("status", "1");
            params.put("dicttypeid", "HOME_POSITION_DICT");
            params.put("queryWhere", "");
            params.put("hasEmpty", false);
            req(RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictCombolist(params), isShowProcess, listener);
        } catch (Exception e) {
            LogUtil.e("获取调车通知单起始位置", e.toString());
        }
    }

    //获取调车通知单到达位置
    public void getDicDataForTransTrainEndPosition(boolean isShowProcess, HttpOnNextListener listener) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("status", "1");
            params.put("dicttypeid", "DESTINATION_DICT");
            params.put("queryWhere", "");
            params.put("hasEmpty", false);
            req(RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictCombolist(params), isShowProcess, listener);
        } catch (Exception e) {
            LogUtil.e("获取调车通知单到达位置", e.toString());
        }
    }
}
