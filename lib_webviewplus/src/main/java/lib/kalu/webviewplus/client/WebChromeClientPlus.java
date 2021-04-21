package lib.kalu.webviewplus.client;

import android.net.Uri;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import lib.kalu.webviewplus.impl.WebChromeClientImpl;

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

    /**
     * dialog - alert
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    /**
     * dialog - confirm
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        onProgressChanged(view, view.getUrl(), newProgress);
    }

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
}