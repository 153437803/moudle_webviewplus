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

import lib.kalu.webviewplus.util.LogUtil;

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
            LogUtil.log("WebViewLoaderImpl", "createWebResourceResponse => mimeType = " + mimeType + ", filepath = " + file.getAbsolutePath());
            return webResourceResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
