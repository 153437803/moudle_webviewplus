package lib.kalu.webviewplus.impl;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewImpl {

    default String initJavascriptInterface() {
        return "android";
    }

    /*****/

    void initConfig(@NonNull WebView webView);

    void initBackground(@NonNull WebView webView);

    void initWebViewClient(@NonNull WebView webView);

    void initWebChromeClient(@NonNull WebView webView);

    /*****/

    void onPageStarted(@NonNull WebView view, @NonNull String url);

    void onPageFinished(@NonNull WebView view, @NonNull String url);

    /*****/

    boolean onJsTimeout();

    boolean onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

    boolean onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

    boolean onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

    boolean onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result);

    /*****/

    void loadJavascriptAssets(@NonNull String fliename);

    void loadJavascriptString(@NonNull String jstring);

    void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress);
}
