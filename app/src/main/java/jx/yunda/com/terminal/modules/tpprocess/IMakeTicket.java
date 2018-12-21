package jx.yunda.com.terminal.modules.tpprocess;

import java.util.List;

import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;

public interface IMakeTicket {
    void onLoadTicktSuccess(List<TicketTrainBean> ticketInfoBeans);
    void onLoadTicktTypeSuccess(List<TicketTypeBean> types);
    void onLoadTicktFail(String msg);
}
