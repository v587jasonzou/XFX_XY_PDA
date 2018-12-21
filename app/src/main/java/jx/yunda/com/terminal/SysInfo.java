package jx.yunda.com.terminal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.modules.ORGBookNew.act.BookNewActivity;
import jx.yunda.com.terminal.modules.ORGBookNew.act.ORGBookActivityNew;
import jx.yunda.com.terminal.modules.ORGBookNew.act.TpWorkNewActivity;
import jx.yunda.com.terminal.modules.confirmNotify.act.NotifyActivity;
import jx.yunda.com.terminal.modules.fixflow.activity.FlowTrainListActivity;
import jx.yunda.com.terminal.modules.jcApply.JCApplyActivity;
import jx.yunda.com.terminal.modules.ORGBook.act.ORGBookActivity;
import jx.yunda.com.terminal.modules.jcApproval.JCApprovalActivity;
import jx.yunda.com.terminal.modules.main.model.MenuConfig;
import jx.yunda.com.terminal.modules.login.model.UserInfo;
import jx.yunda.com.terminal.modules.FJ_ticket.FJTicketActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.act.RecanditionTrainActivity;
import jx.yunda.com.terminal.modules.foremandispatch.ForemanDispatchActivity;
import jx.yunda.com.terminal.modules.main.model.SecondFuncBean;
import jx.yunda.com.terminal.modules.pullTrainNotice.PullTrainNoticeEditActivity;
import jx.yunda.com.terminal.modules.quality.act.QualityActivity;
import jx.yunda.com.terminal.modules.receiveTrain.ReceiveTrainActivity;
import jx.yunda.com.terminal.modules.receiveTrain.fragment.ReceiveTrainNoticeActivity;
import jx.yunda.com.terminal.modules.schedule.act.SchedukeTrainActivity;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleBookActivityNew;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleBookNewActivity;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleTpWorkActivity;
import jx.yunda.com.terminal.modules.takeCarConfirm.act.TakeCarConfirmActivity;
import jx.yunda.com.terminal.modules.transNotice.TransNoticeActivity;
import jx.yunda.com.terminal.modules.specialApproval.SpecialApprovalActivity;
import jx.yunda.com.terminal.modules.tpApproval.TPApprovalActivity;
import jx.yunda.com.terminal.modules.tpmanage.activity.TicketManagerActivity;
import jx.yunda.com.terminal.modules.tpprocess.TP_ProcessActivity;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.WorkshopTaskDispatchActivity;

public final class SysInfo {
    //用户信息
    public static UserInfo userInfo;
    // 系统所有菜单
    public static List<SecondFuncBean> menus;
    // 机车系统菜单
    public static List<SecondFuncBean> trainMenus;
    // 配件系统菜单
    public static List<SecondFuncBean> partMenus;
    public static String baseURL;
    public static String msgWebURL;
    public static String msgSocketAdress;
    public static int msgSocketPort;

    public static Map<String, MenuConfig> menuConfigMap = new HashMap<>();

    static {
        menuConfigMap.put("JT-28提票", new MenuConfig(R.mipmap.tp_process, R.color.white, TP_ProcessActivity.class));
        menuConfigMap.put("机车状态", new MenuConfig(R.mipmap.trainfix, R.color.white, FlowTrainListActivity.class));
        menuConfigMap.put("提票处理", new MenuConfig(R.mipmap.tp_manage_icon, R.color.white, TicketManagerActivity.class));
        menuConfigMap.put("复检提票", new MenuConfig(R.mipmap.tp_fj_ticket, R.color.white, FJTicketActivity.class));
        menuConfigMap.put("范围检查", new MenuConfig(R.mipmap.trainfix, R.color.white, RecanditionTrainActivity.class));
          //menuConfigMap.put("配件检修", new MenuConfig(R.mipmap.equipfix, R.color.white, MessageActivity.class));
        menuConfigMap.put("质量检查", new MenuConfig(R.mipmap.quality_icon, R.color.white, QualityActivity.class));
        menuConfigMap.put("工长派工", new MenuConfig(R.mipmap.bzpg_icon, R.color.white, ForemanDispatchActivity.class));
        menuConfigMap.put("车间任务调度", new MenuConfig(R.mipmap.cjrwdd_icon, R.color.white, WorkshopTaskDispatchActivity.class));
        menuConfigMap.put("调车通知", new MenuConfig(R.mipmap.shunt_notice, R.color.white, TransNoticeActivity.class));
        menuConfigMap.put("班组点名", new MenuConfig(R.mipmap.bzdm_icon, R.color.white, BookNewActivity.class));
        menuConfigMap.put("复检点名", new MenuConfig(R.mipmap.fjdm, R.color.white, ORGBookActivity.class));
        menuConfigMap.put("交车申请", new MenuConfig(R.mipmap.jc_apply, R.color.white, JCApplyActivity.class));
        menuConfigMap.put("交车审批", new MenuConfig(R.mipmap.jc_approval, R.color.white, JCApprovalActivity.class));
        menuConfigMap.put("提票鉴定", new MenuConfig(R.mipmap.special_approval, R.color.white, TPApprovalActivity.class));
        menuConfigMap.put("例外放行", new MenuConfig(R.mipmap.special_approval, R.color.white, SpecialApprovalActivity.class));
        menuConfigMap.put("调度接车", new MenuConfig(R.mipmap.ddjc_icon,R.color.white, ReceiveTrainNoticeActivity.class));
        menuConfigMap.put("调车确认", new MenuConfig(R.mipmap.dcqr_icon, R.color.white, NotifyActivity.class));
        menuConfigMap.put("生产调度", new MenuConfig(R.mipmap.scdd_icon, R.color.white, ScheduleBookActivityNew.class));
        menuConfigMap.put("牵车确认", new MenuConfig(R.mipmap.dcqr_icon, R.color.white, TakeCarConfirmActivity.class));
        menuConfigMap.put("牵车通知", new MenuConfig(R.mipmap.shunt_notice, R.color.white, PullTrainNoticeEditActivity.class));
        menuConfigMap.put("提票调度派工", new MenuConfig(R.mipmap.dcqr_icon, R.color.white, ScheduleTpWorkActivity.class));
        menuConfigMap.put("提票工长派工", new MenuConfig(R.mipmap.shunt_notice, R.color.white, TpWorkNewActivity.class));
    }

    public static void InitInfo() {
        userInfo = null;
        menus = null;
        trainMenus = null;
        partMenus = null;
    }
}
