package lib.kalu.webviewplus.client;

import android.net.Uri;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import lib.kalu.webviewplus.impl.WebChromeClientImpl;
import lib.kalu.webviewplus.impl.WebViewImpl;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public class WebChromeClientPlus extends WebChromeClient implements WebChromeClientImpl {

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        super.onShowCustomView(view, requestedOrientation, callback);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onJsAlert(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onJsConfirm(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onJsPrompt(view, url, message, defaultValue, result);
        }
        return true;
    }

    @Override
    public boolean onJsTimeout() {
        return super.onJsTimeout();
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        if (null != view && view instanceof WebViewImpl && null != url && url.length() > 0) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onJsBeforeUnload(view, url, message, result);
        }
        return true;
    }

    /*********/

    /**
     * 根据这个回调，可以控制进度条的进度（包括显示与隐藏）。一般情况下，想要达到100%的进度需要的时间较长（特别是首次加载），用户长时间等待进度条不消失必定会感到焦虑，影响体验。其实当progress达到80的时候，加载出来的页面已经基本可用了。事实上，国内厂商大部分都会提前隐藏进度条，让用户以为网页加载很快。
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (null != view && view instanceof WebViewImpl) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onProgressChanged(view, view.getUrl(), newProgress);
            LogUtil.log("WebChromeClientPlus", "onProgressChanged => newProgress = " + newProgress + ", targetUrl = " + view.getUrl());
        }
    }

    /*********/

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        printConsoleMessage("WebChromeClientPlus", consoleMessage);
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        printConsoleMessage("WebChromeClientPlus", message);
        super.onConsoleMessage(message, lineNumber, sourceID);
    }

    /*********/
}