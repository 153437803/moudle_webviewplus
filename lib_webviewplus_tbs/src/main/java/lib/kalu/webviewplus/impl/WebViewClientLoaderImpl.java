package lib.kalu.webviewplus.impl;

import android.content.Context;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
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
    default File createWebplusDir(@NonNull com.tencent.smtt.sdk.WebView view) {

        // 判断本地资源是否存在
        Context context = view.getContext().getApplicationContext();
        File filesDir = context.getFilesDir();

        File webplusFile = new File(filesDir, "webplus");
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
    default com.tencent.smtt.export.external.interfaces.WebResourceResponse createWebResourceResponse(@NonNull String mimeType, @NonNull File file) {

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse = new com.tencent.smtt.export.external.interfaces.WebResourceResponse(mimeType, "UTF-8", fileInputStream);
            LogUtil.log("WebViewLoaderImpl", "createWebResourceResponse => mimeType = " + mimeType + ", filepath = " + file.getAbsolutePath());
            return webResourceResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
