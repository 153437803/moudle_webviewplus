package lib.kalu.webviewplus.impl;

import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * description: WebView - IO - Loader
 * created by kalu on 2021-04-20
 */
public interface WebViewClientLoaderImpl {

    /**
     * 创建缓存资源目录
     *
     * @param view
     * @return
     */
    default File createWebplusDir(@NonNull WebView view) {

        // 判断本地资源是否存在
        Context context = view.getContext().getApplicationContext();
        File filesDir = context.getFilesDir();

        File webplusFile = new File(filesDir, "webviewplus");
        if (null != webplusFile && webplusFile.isFile()) {
            webplusFile.delete();
        }
        if (!webplusFile.exists()) {
            webplusFile.mkdir();
        }

        return webplusFile;
    }

    /**
     * 创建本地资源响应
     *
     * @param mimeType
     * @param file
     * @return
     */
    default WebResourceResponse createWebResourceResponse(@NonNull String mimeType, @NonNull File file) {

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            WebResourceResponse webResourceResponse = new WebResourceResponse(mimeType, "UTF-8", fileInputStream);
            // Log.d("WebViewLoaderImpl", "loadResoruce => mimeType = " + mimeType + ", filepath = " + file.getAbsolutePath());
            return webResourceResponse;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 缓存资源文件
     *
     * @param fileUrl
     * @param finePath
     * @return
     */
    default boolean downloadResoruce(@NonNull String fileUrl, @NonNull String finePath) {

        try {

            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            //下载后的文件名
            File file = new File(finePath);
            if (file.exists()) {
                file.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(file.getAbsolutePath());
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
            Log.d("WebViewLoaderImpl", "downloadResoruce => status = true, fileUrl = " + fileUrl + ", filePath = " + file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            Log.d("WebViewLoaderImpl", "downloadResoruce => status = false, fileUrl = " + fileUrl + ", filePath = null");
            return false;
        }
    }
}
