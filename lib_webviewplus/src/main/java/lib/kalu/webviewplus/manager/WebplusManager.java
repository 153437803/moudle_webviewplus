package lib.kalu.webviewplus.manager;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import java.util.Stack;

import lib.kalu.webviewplus.WebViewPlus;
import lib.kalu.webviewplus.provider.WebplusProvider;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-26
 */
public final class WebplusManager {

    private final static Stack<WebView> stack = new Stack<>();

    private WebplusManager() {
        for (int i = 0; i < 4; i++) {
            WebViewPlus webView = new WebViewPlus(WebplusProvider.mWebplusContext);
            webView.loadUrl("file:///android_asset/netInit.html");
            LogUtil.log("WebplusManager", "WebplusManager => i = " + i + ", webview = " + webView);
            stack.push(webView);
        }
    }

    public static class Holder {
        static WebplusManager WebplusManager = new WebplusManager();
    }

    public final static void init() {
        WebplusManager WebplusManager = Holder.WebplusManager;
        LogUtil.log("WebplusManager", "init => size = " + stack.size());
    }

    public final static WebView pop(@NonNull Context context) {

        if (stack.size() == 0) {
            WebViewPlus webView = new WebViewPlus(context);
            webView.loadUrl("file:///android_asset/netInit.html");
            stack.push(webView);
        }

        WebView webView = stack.pop();

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
            }catch (Exception e){
            }
        }

        LogUtil.log("WebplusManager", "pop => size = " + stack.size());
        return webView;
    }

    public final static void push(@NonNull WebView webView) {

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
            }catch (Exception e){
            }
        }

        stack.push(webView);
        LogUtil.log("WebplusManager", "push => size = " + stack.size());
    }
}
