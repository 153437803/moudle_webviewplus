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