package jx.yunda.com.terminal.common.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import jx.yunda.com.terminal.common.logcat.Logcat;

/**
 * @Description:错误日志打印功能类
 * @author: 周雪巍
 * @time: 2018/8/3 11:50
 * **/
public class LogWriter {
    private static final boolean DEBUG = true;
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XYXFX/log/";
    public static final String FILE_NAME = "crash";
    public static final String FILE_NAME_SUFFIX = ".txt";
    private static File file;

    //写入日志相关信息
    public static synchronized void writeLog(Context context, Throwable ex, File file, String time, WriteCallback writeCallback) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Logcat.w("sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            writeMobileInfo(context, pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
            writeCallback.writeSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            Logcat.e("write crash exception failed");
        }
    }

    //写入设备相关信息
    private static void writeMobileInfo(Context context, PrintWriter pw) throws PackageManager.NameNotFoundException {

        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version：");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        //Android版本
        pw.print("OS Version：");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor：");
        pw.println(Build.MANUFACTURER);

        pw.print("Model：");
        pw.println(Build.MODEL);
    }

    public interface WriteCallback {
        void writeSuccess();
    }
}
