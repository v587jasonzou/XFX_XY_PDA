package jx.yunda.com.terminal.common.crash;

import java.io.File;

/**
 * @Description:错误日志上传回调接口
 * @author: 周雪巍
 * @time: 2018/8/3 11:50
 * **/
public interface HttpReportCallback {

    void uploadException2remote(File file);
}
