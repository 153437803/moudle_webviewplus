package lib.kalu.webviewplus.impl;

import android.webkit.ConsoleMessage;
import android.webkit.WebView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.webviewplus.util.LogUtil;

/**
 * description: WebChromeClientImpl
 * created by kalu on 2021-04-21
 */
public interface WebChromeClientImpl {

    /**
     * 打印h5日志
     *
     * @param tag
     * @param consoleMessage
     */
    default void printConsoleMessage(@NonNull String tag, @Nullable ConsoleMessage consoleMessage) {
        if (null != consoleMessage && null != consoleMessage.message() && consoleMessage.message().length() > 0) {
            printConsoleMessage(tag, consoleMessage.message());
        }
    }

    /**
     * 打印h5日志
     *
     * @param tag
     * @param msg
     */
    default void printConsoleMessage(@NonNull String tag, @NonNull String msg) {
        LogUtil.log(tag, "printConsoleMessage => msg = " + msg);
    }

    /**
     * @param view
     * @param newProgress
     */
    default void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress) {
        if (null != view && view instanceof WebViewImpl) {
            WebViewImpl webViewImpl = (WebViewImpl) view;
            webViewImpl.onProgressChanged(view, targetUrl, newProgress);
            LogUtil.log("WebChromeClientImpl", "onProgressChanged => newProgress = " + newProgress + ", targetUrl = " + targetUrl);
        }
    }
}
