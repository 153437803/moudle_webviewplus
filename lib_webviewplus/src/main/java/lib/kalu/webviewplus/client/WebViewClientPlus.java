package lib.kalu.webviewplus.client;

import android.net.http.SslError;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

import lib.kalu.webviewplus.impl.WebViewClientImpl;
import lib.kalu.webviewplus.util.FileUtil;
import lib.kalu.webviewplus.util.LogUtil;
import lib.kalu.webviewplus.util.MD5Util;

/**
 * description: WebViewClient
 * created by kalu on 2021-04-20
 */
public class WebViewClientPlus extends WebViewClient implements WebViewClientImpl {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    /************/

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return loadResourceUrl(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return loadResourceUrl(view, request.getUrl().toString());
    }

    /************/

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        // super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtil.log("WebViewClientPlus", "onReceivedError1 => errorCode = " + errorCode + ", description" + description);

        // 加载错误本地静态资源
        loadResourceFail(view);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        // super.onReceivedError(view, request, error);
        LogUtil.log("WebViewClientPlus", "onReceivedError2 => errorCode = " + error.getErrorCode() + ", description" + error.getDescription());

        // 加载错误本地静态资源
        loadResourceFail(view);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        LogUtil.log("WebViewClientPlus", "onReceivedError3 => statusCode = " + errorResponse.getStatusCode());
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // super.onReceivedSslError(view, handler, error);

        // 接受证书
        handler.proceed();
    }

    @Override
    public WebResourceResponse loadResourceUrl(@NonNull WebView view, @NonNull String url) {
        // Log.d("WebViewClientPlus", "loadWebResource => url = " + url);

        int lastIndexOf = url.lastIndexOf("/");
        String realUrl = url.substring(lastIndexOf, url.length());

        String suffix = null;
        int i = realUrl.lastIndexOf(".");

        // 未发现后缀, 默认*.unknow
        if (i == -1) {
            suffix = ".unknow";
        }
        // 正常
        else {
            suffix = realUrl.substring(i, realUrl.length());
        }

        // 容错"/"
        String format = realUrl.trim().toLowerCase().replaceAll("/", "");

        File webplusDir = createWebplusDir(view);
        String filename = webplusDir.getAbsolutePath() + File.separator + MD5Util.getMD5Str(format) + suffix;

        File file = new File(filename);
        String mimeType = FileUtil.getMimeType(filename);

        // 未发现mimeType, 即未网络请求
        if (null == mimeType || mimeType.length() == 0 || mimeType.equals("application/json;charset=utf-8") || mimeType.startsWith("application/json;")) {

            LogUtil.log("WebViewClientPlus", "loadWebResource[缓存-接口数据] => mimeType = " + mimeType + ", url = " + url);
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            return webResourceResponse;
        }
        // 资源文件, 存在
        else if (null != file && file.exists()) {

            WebResourceResponse webResourceResponse = createWebResourceResponse(mimeType, file);

            // 解析失败, 当次不在进行缓存策略
            if (null == webResourceResponse) {

                LogUtil.log("WebViewClientPlus", "loadWebResource[缓存-解析失败] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                file.delete();
                return super.shouldInterceptRequest(view, url);
            }
            // 解析成功
            else {

                LogUtil.log("WebViewClientPlus", "loadWebResource[缓存-解析成功] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                return webResourceResponse;
            }
        }
        // 资源文件, 不存在
        else {

            // 下载状态
            boolean status = downloadResoruce(url, file.getAbsolutePath());

            if (status) {
                LogUtil.log("WebViewClientPlus", "loadWebResource[网络-下载成功] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                WebResourceResponse webResourceResponse = createWebResourceResponse(mimeType, file);
                return webResourceResponse;
            } else {
                LogUtil.log("WebViewClientPlus", "loadWebResource[网络-下载失败] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
                return webResourceResponse;
            }
        }
    }
}