package lib.kalu.webviewplus.impl;

import android.app.Activity;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import lib.kalu.webviewplus.init.WebplusProvider;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewImpl {

    default String initJavascriptInterface() {
        return "android";
    }

    default String initAssetDefaultInitResourceName() {
        return "WpsPlusNetInit.html";
    }

    default String initAssetDefaultFailResourceName() {
        int heightPixels = WebplusProvider.mWebplusContext.getResources().getDisplayMetrics().heightPixels;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WpsPlusNetError.html");
        stringBuilder.append(heightPixels * 0.3f);
        return stringBuilder.toString();
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

    default void onBackPressed(@NonNull Activity activity){
        onBackPressed(activity, false);
    }

    void onBackPressed(@NonNull Activity activity, boolean tryAgin);

    void loadJavascriptAssets(@NonNull String fliename);

    void loadJavascriptString(@NonNull String jstring);

    void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress);
}
