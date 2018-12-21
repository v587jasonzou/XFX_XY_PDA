package jx.yunda.com.terminal.modules.message;

import android.content.Intent;
import android.os.Bundle;

import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.modules.ORGBook.act.ORGBookActivity;
import jx.yunda.com.terminal.modules.ORGBookNew.act.BookNewActivity;
import jx.yunda.com.terminal.modules.ORGBookNew.act.ORGBookActivityNew;
import jx.yunda.com.terminal.modules.confirmNotify.act.CofirmNotifyActivity;
import jx.yunda.com.terminal.modules.jcApproval.JCApprovalActivity;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.modules.receiveTrain.ReceiveTrainActivity;
import jx.yunda.com.terminal.modules.receiveTrain.fragment.ReceiveTrainNoticeActivity;
import jx.yunda.com.terminal.modules.receiveTrain.fragment.ReceiveTrainOperationActivity;
import jx.yunda.com.terminal.modules.schedule.act.SchedukeTrainActivity;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleBookNewActivity;
import jx.yunda.com.terminal.modules.specialApproval.SpecialApprovalActivity;
import jx.yunda.com.terminal.modules.tpApproval.TPApprovalActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;

public class TransOtherModules {
    MessageActivity mActivity;

    public TransOtherModules(MessageActivity mActivity) {
        this.mActivity = mActivity;
    }
    /*
     * 若打开Fragment 活动为MessageActivity 容器的id为R.id.message_panel；若直接打开活动则调用ActivityUtil.startActivityNotInActivity
     *businessId    businessType        webOpenMode(0：不打开页面；1：打开模块主页面；2：打开模块子页面)
     *JX-SCDD-FW	生产调度-范围         1
     *JX-SCDD-JT28	生产调度-JT28         1
     *JX-BZDM-FW1	班组点名-范围1        1
     *JX-BZDM-FW2	班组点名-范围2        1
     *JX-BZDM-JT28	班组点名-JT28         1
     *JX-FWJC	    范围检查              0
     *JX-TPCL	    提票处理              0
     *JX-ZLJC-FW	质量检查-范围         0
     *JX-ZLJC-JT28	质量检查-JT28         0
     *JX-DCTZ	    调车通知              2
     *JX-DDJC	    调度接车              2
     *JX-TPZC	    提票支持              1
     *JX-FXSP	    放行审批              1
     *JX-JCSP	    交车审批              1
     *JX-JDYQ	    节点延期              0
     * */

    public void transOtherModulesByMsg(MessageInfo msg) {
        if (!"1".equals(msg.getWebOpenMode()) && !"2".equals(msg.getWebOpenMode())) return;
        switch (msg.getBusinessType()) {
            case "提票支持":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), TPApprovalActivity.class, null);
                break;
            case "放行审批":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), SpecialApprovalActivity.class, null);
                break;
            case "交车审批":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), JCApprovalActivity.class, null);
                break;
            case "调车通知":
                Bundle bundle = new Bundle();
                bundle.putString("idx",msg.getLinkParam());
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), CofirmNotifyActivity.class, bundle);
                break;
            case "调度接车":
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("idx",msg.getLinkParam());
//                bundle1.putBoolean("ismessge",true);
//                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), ReceiveTrainNoticeActivity.class, bundle1);
                break;
            case "生产调度-范围":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), ScheduleBookNewActivity.class, null);
                break;
            case "生产调度-JT28":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), ScheduleBookNewActivity.class, null);
                break;
            case "班组点名-范围1":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), BookNewActivity.class, null);
                break;
            case "班组点名-范围2":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), BookNewActivity.class, null);
                break;
            case "班组点名-JT28":
                ActivityUtil.startActivityNotInActivity(JXApplication.getContext(), BookNewActivity.class, null);
                break;
            default:
                break;

        }
    }

}
