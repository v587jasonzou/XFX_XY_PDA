package jx.yunda.com.terminal.modules.fixflow.presenter;

import java.util.List;
import jx.yunda.com.terminal.modules.fixflow.entry.TicketInfoBean;

public interface iGetTpList {
    void LoadTpListSuccess(List<TicketInfoBean> list);
    void LoadMoreTpListSuccess(List<TicketInfoBean> list);
    void LoadFaild(String msg);
}
