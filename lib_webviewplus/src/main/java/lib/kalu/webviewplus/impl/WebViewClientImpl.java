package lib.kalu.webviewplus.impl;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import lib.kalu.webviewplus.R;

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
    default void loadResourceFail(@NonNull WebView webView, @NonNull String assetFileName) {

        if (null != webView && null != webView.getContext()) {

            if (null == webView.getTag(R.id.id_webviewplus_targeturl) && null != webView.getUrl() && webView.getUrl().length() > 0 && webView.getUrl().startsWith("http")) {
                webView.setTag(R.id.id_webviewplus_targeturl, webView.getUrl());
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/");
            stringBuilder.append(assetFileName);

            // 网络错误, 加载本地Html
            String localUrl = stringBuilder.toString();
            webView.loadUrl(localUrl);
        }
    }
}
