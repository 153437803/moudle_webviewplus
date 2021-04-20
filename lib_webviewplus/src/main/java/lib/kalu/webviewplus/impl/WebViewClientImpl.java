package lib.kalu.webviewplus.impl;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewClientImpl {

    default void loadFailResource(@NonNull WebView webView) {

        if (null != webView && null != webView.getContext()) {

            Context context = webView.getContext().getApplicationContext();
            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/default.html?height=");
            stringBuilder.append(heightPixels * 0.3f);

            // 网络错误, 加载本地Html
            String localUrl = stringBuilder.toString();
            webView.loadUrl(localUrl);
        }
    }

    WebResourceResponse loadWebResource(@NonNull WebView view, @NonNull String url);
}
