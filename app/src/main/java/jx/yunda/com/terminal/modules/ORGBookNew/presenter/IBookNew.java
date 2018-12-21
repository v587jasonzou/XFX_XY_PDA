package jx.yunda.com.terminal.modules.ORGBookNew.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookUserBeanNew;
import jx.yunda.com.terminal.modules.ORGBookNew.model.Nodebean;


public interface IBookNew {
    void BookSuccess();
    void BookFaild(String msg);
    void PostPlanSuccess();
    void PostPlanFaild(String msg);
    void getUsersSuccess(List<BookUserBean> list);
    void getUsersFaild(String msg);
    void getUserPlanSuccess(List<BookLastUserBean> list);
    void getUserPlanFaild(String msg);
    void getBookCalenderSuccess(BookCalenderBean list);
    void getBookCalenderFaild(String msg);
    void getUsersSuccessNew(List<BookUserBeanNew> list);
    void getBookPlanSuccess(List<Nodebean> list);
    void getUnBookPlanSuccess(List<Nodebean> list);
    void OnAddSuccess();
    void OnDeleteSuccess();
}
