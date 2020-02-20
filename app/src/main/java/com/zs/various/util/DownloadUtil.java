package com.zs.various.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.zs.various.application.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtil {

    private Context mContext;
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static DownloadUtil getInstance(Context context) {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil(context);
        }
        return downloadUtil;
    }

    private DownloadUtil(Context context) {
        okHttpClient = new OkHttpClient();
        this.mContext = context;
    }

    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                new Handler(MyApp.getAppContext().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        new Handler(MyApp.getAppContext().getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloading(progress);
                            }
                        });
                    }
                    fos.flush();
                    // 下载完成
                    new Handler(MyApp.getAppContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess();
                        }
                    });
                } catch (Exception e) {
                    new Handler(MyApp.getAppContext().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed();
                        }
                    });
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public String getApkDir() {
        String apkPath = Environment.getExternalStorageDirectory() + "/" + "AAYFZX";
        return apkPath;
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog() {
        // 构造对话框
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle("正在更新")
                .setMessage("2.2.2")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 显示下载对话框
//                        showDownloadDialog();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (listener != null) {
//                            listener.cancelUpdate();
//                        }
                        dialog.dismiss();
                    }
                }).create();


        alertDialog.show();

    }

}
