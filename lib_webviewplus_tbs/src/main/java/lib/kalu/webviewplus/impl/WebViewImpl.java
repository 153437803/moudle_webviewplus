package lib.kalu.webviewplus.impl;

import android.app.Activity;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import lib.kalu.webviewplus.init.InitProvider;

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
        int heightPixels = InitProvider.mWebplusContext.getResources().getDisplayMetrics().heightPixels;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WpsPlusNetError.html");
        stringBuilder.append(heightPixels * 0.3f);
        return stringBuilder.toString();
    }

    /*****/

    void initConfig(@NonNull com.tencent.smtt.sdk.WebView webView);

    void initBackground(@NonNull com.tencent.smtt.sdk.WebView webView);

    void initWebViewClient(@NonNull com.tencent.smtt.sdk.WebView webView);

    void initWebChromeClient(@NonNull com.tencent.smtt.sdk.WebView webView);

    /*****/

    void onPageStarted(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url);

    void onPageFinished(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url);

    /*****/

    boolean onJsTimeout();

    boolean onJsBeforeUnload(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

    boolean onJsAlert(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

    boolean onJsConfirm(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

    boolean onJsPrompt(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull com.tencent.smtt.export.external.interfaces.JsPromptResult result);

    /*****/

    default void onBackPressed(@NonNull Activity activity){
        onBackPressed(activity, false);
    }

    void onBackPressed(@NonNull Activity activity, boolean tryAgin);

    void loadJavascriptAssets(@NonNull String fliename);

    void loadJavascriptString(@NonNull String jstring);

    void onProgressChanged(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress);
}
