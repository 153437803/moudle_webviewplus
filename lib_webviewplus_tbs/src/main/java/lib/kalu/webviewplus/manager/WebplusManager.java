package lib.kalu.webviewplus.manager;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import java.util.Stack;

import lib.kalu.webviewplus.R;
import lib.kalu.webviewplus.WebViewPlus;
import lib.kalu.webviewplus.init.InitProvider;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-26
 */
public class WebplusManager {

    private static Stack<com.tencent.smtt.sdk.WebView> stack = new Stack<>();

    private WebplusManager() {
        for (int i = 0; i < 4; i++) {
            WebViewPlus webView = new WebViewPlus(InitProvider.mWebplusContext);
            String resourceName = webView.initAssetDefaultInitResourceName();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/");
            stringBuilder.append(resourceName);

            String url = stringBuilder.toString();
            webView.setTag(R.id.id_webplus_assets_url_init, url);
            webView.loadUrl(url);
            LogUtil.log("WebplusManager", "WebplusManager => i = " + i + ", webview = " + webView);
            stack.push(webView);
        }
    }

    public static class Holder {
        static WebplusManager WebplusManager = new WebplusManager();
    }

    public static void init() {
        WebplusManager WebplusManager = Holder.WebplusManager;
        LogUtil.log("WebplusManager", "init => size = " + stack.size());
    }

    public static com.tencent.smtt.sdk.WebView pop(@NonNull Context context) {

        if (stack.size() == 0) {
            WebViewPlus webView = new WebViewPlus(context);
            webView.loadUrl("file:///android_asset/WpsPlusNetInit.html");
            stack.push(webView);
        }

        com.tencent.smtt.sdk.WebView webView = stack.pop();

        while (webView.canGoBack()) {
            webView.goBack();
        }

        webView.clearCache(true);
        webView.clearFormData();
        webView.clearMatches();
        webView.clearSslPreferences();
        webView.clearHistory();

        if (null != webView.getParent()) {
            try {
                ViewGroup viewGroup = (ViewGroup) webView.getParent();
                viewGroup.removeView(webView);
            } catch (Exception e) {
            }
        }

        LogUtil.log("WebplusManager", "pop => size = " + stack.size());
        return webView;
    }

    public static void push(@NonNull com.tencent.smtt.sdk.WebView webView) {

        if (null == webView)
            return;

        while (webView.canGoBack()) {
            webView.goBack();
        }

        webView.clearCache(true);
        webView.clearFormData();
        webView.clearMatches();
        webView.clearSslPreferences();
        webView.clearHistory();

        if (null != webView.getParent()) {
            try {
                ViewGroup viewGroup = (ViewGroup) webView.getParent();
                viewGroup.removeView(webView);
            } catch (Exception e) {
            }
        }

        stack.push(webView);
        LogUtil.log("WebplusManager", "push => size = " + stack.size());
    }
}
