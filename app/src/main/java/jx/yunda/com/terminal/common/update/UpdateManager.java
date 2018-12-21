package jx.yunda.com.terminal.common.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * <li>标题: 机车整备检修信息系统-手持终端
 * <li>说明: 终端应用更新管理
 * <li>创建人：何东
 * <li>创建日期：2016年5月14日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2015 运达科技公司
 * 
 * @author 何东
 * @version 1.0
 */
public class UpdateManager {
    
    /* 检查更新结束通知后续处理 */
    private static final int CHECK_UPDATE = 0;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    
    /* 检查更新_没有更新 */
    private static final int CHECK_UPDATE_OLD = 0;
    /* 检查更新_存在更新 */
    private static final int CHECK_UPDATE_NEW = 1;
    
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private AlertDialog mDownloadDialog;
    /* 提示更新窗口 */
    private AlertDialog noticeDialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            // 更新检查结果
            case CHECK_UPDATE:
                if (msg.arg1 == CHECK_UPDATE_NEW) {
                    // 显示提示对话框
                    showNoticeDialog();
                } else {
                    //Toast.makeText(mContext, R.string.app_update_no, Toast.LENGTH_LONG).show();
                }
                break;
            // 正在下载
            case DOWNLOAD:
                // 设置进度条位置
                mProgress.setProgress(progress);
                break;
            case DOWNLOAD_FINISH:
                // 取消下载对话框显示
                mDownloadDialog.dismiss();
                // 安装文件
                installApk();
                break;
            default:
                break;
            }
        };
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        new Thread() {
            public void run() {
                Message msg = new Message();
                msg.what = CHECK_UPDATE;
                boolean isUpdate = isUpdate();
                if (isUpdate) {
                    msg.arg1 = CHECK_UPDATE_NEW;
                } else {
                    msg.arg1 = CHECK_UPDATE_OLD;
                }
                
                mHandler.sendMessage(msg);
            };
        }.start();
    }

    /**
     * 检查软件是否有更新版本
     * 
     * @return
     */
    private boolean isUpdate() {
        // 获取当前软件版本
        Map<String, Object> versionInfo = getVersionCode(mContext);
        
        if(TextUtils.isEmpty(RxRetrofitApp.getBaseUrl())){
            //MyApp.toastLong("无法连接服务端，请配置正确的服务端地址");
            return false;
        }
        String url = RxRetrofitApp.getBaseUrl();
        char ch = url.charAt(url.length() - 1);
        if(ch != '/')   url = url + "/";
        
        url = url + StringConstants.URL_PDA_APP_UPDATE;
        
        InputStream inStream = null;
        try {
            // 创建一个url对象
            URL urlObj = new URL(url);
            // 通過url对象，创建一个HttpURLConnection对象（连接）  
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestMethod("POST");
            // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
            inStream = urlConnection.getInputStream();
        
            mHashMap = parseXml(inStream);
            
            if (null != mHashMap && mHashMap.size() > 0) {
                // 判断更新信息xml文件中的appName节点中 app名称是否包含 .apk，如果没有包含则添加后缀
                String appName = mHashMap.get("appName");
                if (!TextUtils.isEmpty(appName)) {
                    String lowCaseName = appName.toLowerCase();
                    int idx = lowCaseName.lastIndexOf(".apk");
                    if (idx < 0) {
                        appName += ".apk";
                        mHashMap.put("appName", appName);
                    }
                }
                
                int versionCode = Integer.valueOf(mHashMap.get("versionCode"));
                int versionCodeLocal = (Integer)versionInfo.get("versionCode");
                
                // String versionName  = mHashMap.get("versionName");
                // String versionNameLocal  = (String)versionInfo.get("versionName");
                
                // 版本判断
                if (versionCode > versionCodeLocal) {//  || !versionNameLocal.equals(versionName)
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtil.e("UpdateManager", e.getMessage());
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (Exception e2) {
                LogUtil.e("UpdateManager", e2.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * 解析xml
     * 
     * @param inStream
     * @return
     * @throws Exception
     */
    private HashMap<String, String> parseXml(InputStream inStream) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        
        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档通过文档构建器构建一个文档实例
        Document document = builder.parse(inStream);
        // 获取XML文件根节点
        Element root = document.getDocumentElement();
        // 获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            // 遍历子节点
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                // 版本号
                if ("versionCode".equals(childElement.getNodeName())) {
                    hashMap.put("versionCode",childElement.getFirstChild().getNodeValue());
                }
                // versionCode
                else if (("versionName".equals(childElement.getNodeName()))) {
                    hashMap.put("versionName",childElement.getFirstChild().getNodeValue());
                }
                // 软件名称
                else if (("appName".equals(childElement.getNodeName()))) {
                    hashMap.put("appName",childElement.getFirstChild().getNodeValue());
                }
                // 下载地址
                else if (("url".equals(childElement.getNodeName()))) {
                    hashMap.put("url",childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }

    /**
     * 获已安装取软件版信息
     * 
     * @param context
     * @return
     */
    private Map<String, Object> getVersionCode(Context context) {
        Map<String, Object> pkgInfo = new HashMap<String, Object>();
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageInfo pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            pkgInfo.put("versionCode", pkg.versionCode);
            pkgInfo.put("versionName", pkg.versionName);
        } catch (NameNotFoundException e) {
            Log.e("UpdateManager", e.getMessage());
        }
        return pkgInfo;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //builder.setTitle(R.string.app_update_title);
        builder.setMessage(R.string.app_update_info);
        builder.setCancelable(false);
        // 更新
        builder.setPositiveButton(R.string.app_update_updatebtn,
            new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // 显示下载对话框
                    showDownloadDialog();
                }
            });
        // 稍后更新
        builder.setNegativeButton(R.string.app_update_later,
            new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        noticeDialog = builder.create();
        noticeDialog.show();
        noticeDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.gray));
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.app_updating);
        builder.setCancelable(false);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.app_update_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.app_update_cancel,
            new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // 设置取消状态
                    cancelUpdate = true;
                }
            });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        mDownloadDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(mHashMap.get("url"));
                    HttpResponse httpResponse = client.execute(post);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        // 请求和响应都成功了
                        HttpEntity httpEntity = httpResponse.getEntity();
                        if(httpEntity != null) {
                            // 获取文件大小
                            long length = httpEntity.getContentLength();
                            // 创建输入流
                            is = httpEntity.getContent();
                            
                            File file = new File(mSavePath);
                            // 判断文件目录是否存在
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            File apkFile = new File(mSavePath, mHashMap.get("appName"));
                            fos = new FileOutputStream(apkFile);
                            int count = 0;
                            // 缓存
                            byte buf[] = new byte[1024];
                            // 写入到文件中
                            do {
                                int numread = is.read(buf);
                                count += numread;
                                // 计算进度条位置
                                progress = (int) (((float) count / length) * 100);
                                // 更新进度
                                mHandler.sendEmptyMessage(DOWNLOAD);
                                if (numread <= 0) {
                                    // 下载完成
                                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                    break;
                                }
                                // 写入文件
                                fos.write(buf, 0, numread);
                            } while (!cancelUpdate);// 点击取消就停止下载.
                        }
                    }
                    
                    /*
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    is = conn.getInputStream();
                    */
                }
            } catch (MalformedURLException e) {
                Log.e("UpdateManager", e.getMessage());
            } catch (IOException e) {
                Log.e("UpdateManager", e.getMessage());
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    Log.e("UpdateManager", e.getMessage());
                }
            }
        }
    };

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("appName"));
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    public AlertDialog getNoticeDialog() {
        return noticeDialog;
    }

    public void setNoticeDialog(AlertDialog noticeDialog) {
        this.noticeDialog = noticeDialog;
    }
}
