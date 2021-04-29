package lib.kalu.webviewplus;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import lib.kalu.webviewplus.core.WebViewCore;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public final class WebViewPlus extends WebViewCore {

    public WebViewPlus(Context context) {
        super(context);
    }

    public WebViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void onPageFinished(@NonNull WebView view, @NonNull String url) {
        super.onPageFinished(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onPageStarted(@NonNull WebView view, @NonNull String url) {
        super.onPageStarted(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress) {
        super.onProgressChanged(view, targetUrl, newProgress);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onProgressChanged(view, targetUrl, newProgress);
        }
    }

    @Override
    public boolean onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsConfirm(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsPrompt(view, url, message, defaultValue, result);
        }
        return true;
    }

    @Override
    public boolean onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onJsAlert(view, url, message, result);
        }
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
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
        void onPageStarted(@NonNull WebView view, @NonNull String url);

        /**
         * 页面加载结束, 用于关闭loading
         *
         * @param view
         * @param url
         */
        void onPageFinished(@NonNull WebView view, @NonNull String url);

        /**
         * 页面加载进度, 用于显示WebView进度条变化
         *
         * @param view
         * @param targetUrl
         * @param newProgress
         */
        void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress);

        boolean onJsTimeout();

        boolean onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

        boolean onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

        boolean onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result);

        boolean onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result);
    }

    /****************/
}
