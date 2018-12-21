package jx.yunda.com.terminal.widget.treeview.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jx.yunda.com.terminal.SysInfo;

/**
 * @Description:
 * @author: ragkan
 * @time: 2016/11/1 14:38
 */
public class Utils {
    public static int SDK = 0;
    public static boolean isOurEquip;
    public static boolean isProblem;
    public static Map<String, String> iamgeMap = new HashMap<>();

    @SuppressLint("SimpleDateFormat")
    public static String getCurformatDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static void setImage(String key, String str) {
        if (iamgeMap != null) {
            iamgeMap.put(key, str);
        }
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
    public static int CellNumber(int a,int b){
        double c = ((double)a)/((double)b);
        return (int) Math.ceil(c);
    }
    public static String getImageBase64(String key) {
        return iamgeMap.get(key);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTime(Date time, String format) {
        // yyyy-MM-dd HH:mm:ss
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 字符串转换成日期，如果需转换的字符串为null，则返回为null
     * <p>
     * String 需转换的字符串
     *
     * @param format String 转换成字符型的日期格式
     * @return Date 日期
     */
    public static Date string2date(String date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static Long stringToLong(String data, String format) {
        if (data == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(data).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //去掉List中的重复元素
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        System.out.println(" remove duplicate " + list);
    }

    /**
     * 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        if (isEmpty(time)) {
            return "传入时间为空";
        }
        return formatTime(Long.parseLong(time), "yyyy-MM-dd HH:mm");
    }

    public static String formatHour(String time) {
        if (isEmpty(time)) {
            return "传入时间为空";
        }
        return formatTime(Long.parseLong(time), "HH:mm");
    }

    public static boolean isEmpty(List<?> list) {
        return (list == null || list.size() == 0);
    }

    public static boolean isEmpty(File file) {
        return file == null;
    }

    public static <T> boolean isEmpty(T[] array) {
        return ((array == null) || (array.length) == 0);
    }

    public static boolean isEmpty(String val) {
        if (val == null || val.matches("\\s") || val.length() == 0
                || "null".equalsIgnoreCase(val) || val.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null || "".equals(obj)) {
            return true;
        }
        return false;
    }

    public static <T> List<T> MapToList(Map<String, T> map) {
        List<T> list = new ArrayList<T>();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            list.add(map.get(key));
        }
        return list;
    }

    public static boolean isMobileNum(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3-8]+\\d{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static void hideKeyboard(Activity activity) {
        try {
            ((InputMethodManager) activity
                    .getSystemService(activity.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equalsIgnoreCase(
                    className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static File saveBitmapFile(Context context, Bitmap bitmap, String path) {
        UUID id = UUID.randomUUID();
        File file = new File(path);// 将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param @param  url
     * @param @param  width
     * @param @param  height
     * @param @return
     * @return Bitmap
     * @Des: 获取视频缩略图
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * 主要是用于ListView的item同样是listView的情况
     *
     * @param @param listView
     * @return void
     * @Des:设置ListView的Adapter后调用此静态方法即可让ListView正确的显示在其父ListView的ListItem中。但是要注意的是，子ListView的每个Item必须是LinearLayout，不能是其他的，
     */

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        float ratio = 1;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawColor(Color.TRANSPARENT);

        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "找不到版本号";
        }
    }


    /**
     * @param context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 只关注是否联网
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static String getBase64StringFromImg(String filePath) {
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        String photoBase64Str = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            //压缩后保存本地
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            int trueWidth = options.outWidth;
            int trueHeigth = options.outHeight;
            int scale = 1;
            options.inSampleSize = scale;

            bitmap = BitmapFactory.decodeFile(filePath, options);
            //转换成base64字符串上传服务器
            bout = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bout);
            bout.flush();
            photoBase64Str = Base64.encodeToString(bout.toByteArray(), Base64.DEFAULT);
            bout.close();

            // 释放bitmap，释放内存
            if (!bitmap.isRecycled())
                bitmap.recycle();
            options.inSampleSize = 4;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoBase64Str;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public static String getImageUrl(String url) {
        String[] str = SysInfo.baseURL.split(":");
        String mUrl = str[0] + ":" + str[1] + ":" + str[2].split("/")[0] + "/" + url;
        return mUrl;
    }
}