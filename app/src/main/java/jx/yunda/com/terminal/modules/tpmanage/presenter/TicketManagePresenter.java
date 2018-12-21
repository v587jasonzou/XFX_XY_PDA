package jx.yunda.com.terminal.modules.tpmanage.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketManagePresenter extends BasePresenter<ITicketManage> {
    public TicketManagePresenter(ITicketManage view) {
        super(view);
    }
    public void getTicketList(int start, int limit, String str, final int type) {
        RequestFactory.getInstance().createApi(TicketApi.class).getTicketList(str, start + "", limit + "").enqueue(new Callback<BaseBean<List<TicketInfoBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TicketInfoBean>>> call, Response<BaseBean<List<TicketInfoBean>>> response) {
                BaseBean<List<TicketInfoBean>> bean = response.body();
                if (bean.isSuccess()) {
                    List<TicketInfoBean> list = bean.getRoot();
                    if (type == 0) {
                        getViewRef().OnTicketListLoadSuccess(list);
                    } else {
                        getViewRef().OnTicketListLoadMoreSuccess(list);
                    }
                } else {
                    getViewRef().OnTicketListLoadFail("获取提票处理记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<TicketInfoBean>>> call, Throwable t) {
                getViewRef().OnTicketListLoadFail("获取提票处理记录失败，请重新访问" + t.getMessage());
            }
        });
    }
}
