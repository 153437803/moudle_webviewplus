package lib.kalu.webviewplus.impl;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;

/**
 * description: WebViewClient - Impl
 * created by kalu on 2021-04-20
 */
public interface WebViewClientImpl extends WebViewClientLoaderImpl {

    /**
     * 加载网络资源
     *
     * @param view
     * @param url
     * @return
     */
    WebResourceResponse loadResourceUrl(@NonNull WebView view, @NonNull String url);

    /**
     * 加载本地错误提示资源
     *
     * @param webView
     */
    default void loadResourceFail(@NonNull WebView webView) {

        if (null != webView && null != webView.getContext()) {

            Context context = webView.getContext().getApplicationContext();
            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/netError.html?height=");
            stringBuilder.append(heightPixels * 0.3f);

            // 网络错误, 加载本地Html
            String localUrl = stringBuilder.toString();
            webView.loadUrl(localUrl);
        }
    }
}
