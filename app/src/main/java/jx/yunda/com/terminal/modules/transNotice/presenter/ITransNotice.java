package jx.yunda.com.terminal.modules.transNotice.presenter;

import java.util.List;

import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;

public interface ITransNotice {
    void pageListNotifyDataSetChanged(String msgGroupId, List<TransNotice> datas, int total);

    void pageListRemoveItem(int position);

    void pageListUpdateItem(TransNotice transNotice, int position);
}
