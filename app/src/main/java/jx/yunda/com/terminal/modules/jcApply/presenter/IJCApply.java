package jx.yunda.com.terminal.modules.jcApply.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.jcApply.model.JCApply;

public interface IJCApply {
    void pageRecyclerNotifyDataSetChanged(List<JCApply> datas);
}
