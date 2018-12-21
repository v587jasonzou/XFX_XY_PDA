package jx.yunda.com.terminal.modules.ORGBook.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;

public interface IBook {
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

}
