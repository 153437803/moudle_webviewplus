package lib.kalu.webviewplus;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.Map;

import lib.kalu.webviewplus.core.WebViewCore;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public class WebViewPlus extends WebViewCore {

    public WebViewPlus(Context context, boolean b) {
        super(context, b);
    }

    public WebViewPlus(Context context) {
        super(context);
    }

    public WebViewPlus(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WebViewPlus(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WebViewPlus(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public WebViewPlus(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    public void onPageFinished(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url) {
        super.onPageFinished(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onPageStarted(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url) {
        super.onPageStarted(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onProgressChanged(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String targetUrl, int newProgress) {
        super.onProgressChanged(view, targetUrl, newProgress);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onProgressChanged(view, targetUrl, newProgress);
        }
    }

    @Override
    public boolean onJsConfirm(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsConfirm(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsPrompt(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull com.tencent.smtt.export.external.interfaces.JsPromptResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsPrompt(view, url, message, defaultValue, result);
        }
        return true;
    }

    @Override
    public boolean onJsAlert(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsAlert(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsBeforeUnload(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsTimeout() {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsTimeout();
        }
        return true;
    }

    /****************/

    private OnWebStatusChangeListener onWebStatusChangeListener;

    @Keep
    public void setOnWebStatusChangeListener(@NonNull OnWebStatusChangeListener listener) {
        this.onWebStatusChangeListener = listener;
    }

    @Keep
    public interface OnWebStatusChangeListener {

        /**
         * 页面加载开始, 用于显示loading
         *
         * @param view
         * @param url
         */
        void onPageStarted(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url);

        /**
         * 页面加载结束, 用于关闭loading
         *
         * @param view
         * @param url
         */
        void onPageFinished(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url);

        /**
         * 页面加载进度, 用于显示WebView进度条变化
         *
         * @param view
         * @param targetUrl
         * @param newProgress
         */
        void onProgressChanged(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String targetUrl, int newProgress);

        /**
         * js超时
         *
         * @return
         */
        void onJsTimeout();

        /**
         * js回调
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        void onJsBeforeUnload(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

        /**
         * 提示框
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        void onJsAlert(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

        /**
         * 确认框
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        void onJsConfirm(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull com.tencent.smtt.export.external.interfaces.JsResult result);

        /**
         * 输入框
         *
         * @param view
         * @param url
         * @param message
         * @param defaultValue
         * @param result
         * @return
         */
        void onJsPrompt(@NonNull com.tencent.smtt.sdk.WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull com.tencent.smtt.export.external.interfaces.JsPromptResult result);
    }

    /****************/
}
