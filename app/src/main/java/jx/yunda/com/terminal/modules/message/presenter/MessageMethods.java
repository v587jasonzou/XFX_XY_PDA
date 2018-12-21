package jx.yunda.com.terminal.modules.message.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.List;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.MessageResultModel;
import jx.yunda.com.terminal.modules.message.okHttp.OkHttpUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MessageMethods {
    public static List<MessageGroupPerson> getGroupPersonsByGroudId(String gId) {
        List<MessageGroupPerson> msgGroupPersons = null;
        try {

            RequestBody body = new FormBody.Builder().add("params", gId).build();
            String result = OkHttpUtil.dealHttp(SysInfo.msgWebURL + "msgGroupUser/getAllGroupUser.do", body);
            MessageResultModel rst = JSON.parseObject(result, MessageResultModel.class);
            if (rst != null && "success".equals(rst.getMsg())) {
                msgGroupPersons = JSON.parseObject(rst.getData(), new TypeReference<List<MessageGroupPerson>>() {
                });
            } else {
                LogUtil.e("消息组人员获取失败", rst.getMsg());
                ToastUtil.toastShort(rst.getMsg());
            }
        } catch (Exception e) {
            msgGroupPersons = null;
            LogUtil.e("消息组人员获取失败", e.getMessage());
        }
        return msgGroupPersons;
    }
}
