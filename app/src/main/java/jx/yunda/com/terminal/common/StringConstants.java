package jx.yunda.com.terminal.common;

public final class StringConstants {
    // 提示信息Start
    // 提示信息 操作成功
    public static final String OPERATE_SUCCESS = "操作成功";
    public static final String OPERATE_FAIL = "操作失败";
    // 提示信息 用户历史纪录提示
    public static final String HISTORY_ACCOUNTS_NULL = "没有历史用户纪录！";
    //提示信息End

    //SharePreferences Start
    // 登录活动的SharePreferences存储文件名
    public static final String ACCOUNT_OF_SHARE_PREFERENCES = "login_data";
    // SharePreferences key 最后一次登录的用户账户
    public static final String LAST_LOGIN_USER_ACCOUNT_KEY = "last_login_user_account";
    // SharePreferences key 存储历史登录的用户
    public static final String HISTORY_USER_ACCOUNT_KEY = "history_user_account";
    // SharePreferences系统基础数据
    public static final String SYSTEM_BASE_DATA = "system_base_data";
    // SharePreferences key 系统请求服务器地址
    public static final String SYSTEM_BASE_URL_KEY = "system_base_url";
    // SharePreferences key 消息WebSocket和服务器地址
    public static final String SYSTEM_MESSAGE_SOCKET_ADDRESS_KEY = "system_message_address";
    public static final String SYSTEM_MESSAGE_SOCKET_PORT_KEY = "system_message_port";
    public static final String SYSTEM_MESSAGE_WEB_URL_KEY = "system_message_web_url";
    //SharePreferences End

    //默认值 Start
    //系统默认服务地址
    public static final String DEFAULT_BASE_URL_VALUE = "http://10.105.238.82:8080/jcjx/";
    /*测试-zouxu*/
//    public static final String DEFAULT_BASE_URL_VALUE = "http://192.168.10.109:8080/jcjx/";
    //消息服务WebSocket地址
    public static final String DEFAULT_MESSAGE_SOCKET_ADDRESS = "10.105.238.82";
    //消息服务默认地址
    public static final String DEFAULT_MESSAGE_WEB_URL = "http://10.105.238.82:8082/Platfrom/";
    //默认值End


    // App更新检查地址
    public static final String URL_PDA_APP_UPDATE = "pdaAppUpdate/pda_jx_version.xml";

    //消息页面PageSign
    //0：非消息页
    public static final String MESSAGE_0 = "0";
    //1：消息群列表页面
    public static final String MESSAGE_1 = "1";
    //2：消息页面，查询消息，初始化消息页面，及历史消息
    public static final String MESSAGE_2 = "2";
    //3：消息页面，发送消息，接收即时消息
    public static final String MESSAGE_3 = "3";
    //3：消息页面，发送即时消息的回执
    public static final String MESSAGE_4 = "4";
    public static final String MESSAGE_SEND_KEY = "message_send_key";
    public static final String MESSAGE_RESPONSE_KEY = "message_response_key";
    public static final String MESSAGE_RESPONSE_BROADCAST_RECIEVER = "jx.yunda.com.terminal.MESSAGE_RESPONSE_BROADCAST_RECIEVER";
    public static final String MESSAGE_SEND_BROADCAST_RECIEVER = "jx.yunda.com.terminal.MESSAGE_SEND_BROADCAST_RECIEVER";
    public static final String MESSAGE_GROUP_BUNDLE_KEY = "messageGroup";
    public static final String MESSAGE_0_KEY = "0";
    public static final String MESSAGE_1_KEY = "1";

    public static final String DIC_JXGC_Fault_Ticket_YYFX_10 = "10";
    public static final String DIC_JXGC_Fault_Ticket_YYFX_20 = "20";
}
