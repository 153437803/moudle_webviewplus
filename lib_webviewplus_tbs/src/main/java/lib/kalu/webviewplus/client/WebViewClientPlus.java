package lib.kalu.webviewplus.client;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

import lib.kalu.webviewplus.R;
import lib.kalu.webviewplus.impl.WebViewClientImpl;
import lib.kalu.webviewplus.impl.WebViewImpl;
import lib.kalu.webviewplus.util.FileUtil;
import lib.kalu.webviewplus.util.LogUtil;
import lib.kalu.webviewplus.util.MD5Util;

/**
 * description: WebViewClient
 * created by kalu on 2021-04-20
 */
public class WebViewClientPlus extends com.tencent.smtt.sdk.WebViewClient implements WebViewClientImpl {

    /**
     * 如果遇到了重定向，或者点击了页面中的a标签实现页面跳转，那么会回调这个方法。可以说这个是WebView里面最重要的回调之一，后面WebView与Native页面交互一节将会详细介绍这个方法。
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * 如果遇到了重定向，或者点击了页面中的a标签实现页面跳转，那么会回调这个方法。可以说这个是WebView里面最重要的回调之一，后面WebView与Native页面交互一节将会详细介绍这个方法。
     * @param view
     * @param request
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideKeyEvent(com.tencent.smtt.sdk.WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    /************/

    /**
     * 无论是普通的页面请求(使用GET/POST)，还是页面中的异步请求，或者页面中的资源请求，都会回调这个方法，给开发一次拦截请求的机会。在这个方法中，我们可以进行静态资源的拦截并使用缓存数据代替，也可以拦截页面，使用自己的网络框架来请求数据。包括后面介绍的WebView免流方案，也和此方法有关。
     * @param view
     * @param url
     * @return
     */
    @Nullable
    @Override
    public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(com.tencent.smtt.sdk.WebView view, String url) {
        return loadResourceUrl(view, url);
    }

    /**
     * 无论是普通的页面请求(使用GET/POST)，还是页面中的异步请求，或者页面中的资源请求，都会回调这个方法，给开发一次拦截请求的机会。在这个方法中，我们可以进行静态资源的拦截并使用缓存数据代替，也可以拦截页面，使用自己的网络框架来请求数据。包括后面介绍的WebView免流方案，也和此方法有关。
     * @param view
     * @param request
     * @return
     */
    @Nullable
    @Override
    public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(com.tencent.smtt.sdk.WebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request) {
        return loadResourceUrl(view, request.getUrl().toString());
    }

    /************/

    @Override
    public void onPageStarted(com.tencent.smtt.sdk.WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0 && url.startsWith("http") && null == view.getTag(R.id.id_webplus_status)) {
            view.setTag(R.id.id_webplus_status, 1);
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onPageStarted(view, url);
        }
    }

    @Override
    public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
        super.onPageFinished(view, url);
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0 && url.startsWith("http") && null != view.getTag(R.id.id_webplus_status)) {
            view.setTag(R.id.id_webplus_status, null);
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onPageFinished(view, url);
        }
    }

    /************/

    @Override
    public void onReceivedError(com.tencent.smtt.sdk.WebView view, int errorCode, String description, String failingUrl) {
        // super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtil.log("WebViewClientPlus", "onReceivedError1 => errorCode = " + errorCode + ", description = " + description);

        // 加载结束回调
        if (null != view && view instanceof WebViewImpl && null != failingUrl && failingUrl.length() > 0 && failingUrl.startsWith("http") && null != view.getTag(R.id.id_webplus_status)) {
            view.setTag(R.id.id_webplus_status, null);
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onPageFinished(view, failingUrl);
        }

        // 加载错误本地静态资源
        if (null != view && view instanceof WebViewImpl) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            String resourceName = webViewImpl.initAssetDefaultFailResourceName();
            loadResourceFail(view, resourceName);
        }
    }

    @Override
    public void onReceivedError(com.tencent.smtt.sdk.WebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceError error) {
        // super.onReceivedError(view, request, error);
        LogUtil.log("WebViewClientPlus", "onReceivedError2 => errorCode = " + error.getErrorCode() + ", description = " + error.getDescription());

        // 加载结束回调
        if (null != view && view instanceof WebViewImpl && null != request.getUrl() && request.getUrl().toString().length() > 0 && request.getUrl().toString().startsWith("http") && null != view.getTag(R.id.id_webplus_status)) {
            view.setTag(R.id.id_webplus_status, null);
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onPageFinished(view, request.getUrl().toString());
        }

        // 加载错误本地静态资源
        if (null != view && view instanceof WebViewImpl) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            String resourceName = webViewImpl.initAssetDefaultFailResourceName();
            loadResourceFail(view, resourceName);
        }
    }

    @Override
    public void onReceivedHttpError(com.tencent.smtt.sdk.WebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        LogUtil.log("WebViewClientPlus", "onReceivedError3 => statusCode = " + errorResponse.getStatusCode());
    }

    @Override
    public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
//        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        // 接受证书
        sslErrorHandler.proceed();
    }

    @Override
    public com.tencent.smtt.export.external.interfaces.WebResourceResponse loadResourceUrl(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url) {
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
            com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            return webResourceResponse;
        }
        // 资源文件, 存在
        else if (null != file && file.exists()) {

            com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse = createWebResourceResponse(mimeType, file);

            // 解析失败, 当次不在进行缓存策略
            if (null == webResourceResponse) {

                LogUtil.log("WebViewClientPlus", "loadWebResource[缓存-解析失败] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                FileUtil.deleteFile(file);
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
            boolean status = FileUtil.writeToLocal(url, file.getAbsolutePath());

            if (status) {
                LogUtil.log("WebViewClientPlus", "loadWebResource[网络-下载成功] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse = createWebResourceResponse(mimeType, file);
                return webResourceResponse;
            } else {
                LogUtil.log("WebViewClientPlus", "loadWebResource[网络-下载失败] => mimeType = " + mimeType + ", url = " + url + ", filePath = " + file.getAbsolutePath());
                FileUtil.deleteFile(file);
                com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
                return webResourceResponse;
            }
        }
    }
}