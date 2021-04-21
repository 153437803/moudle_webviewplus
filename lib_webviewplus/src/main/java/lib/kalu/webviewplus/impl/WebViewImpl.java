package lib.kalu.webviewplus.impl;

import android.webkit.WebView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewImpl {

    void initConfig(@NonNull WebView webView);

    void initBackground(@NonNull WebView webView);

    void initWebViewClient(@NonNull WebView webView);

    void initWebChromeClient(@NonNull WebView webView);

    void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress);
}
