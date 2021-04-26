package lib.kalu.webviewplus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.webviewplus.manager.WebplusManager;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public final class WebViewLayout extends FrameLayout {

    public WebViewLayout(@NonNull Context context) {
        super(context);
        WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public void loadUrl(@NonNull String url) {

        if (null == url || url.length() == 0)
            return;

        View child = getChildAt(0);
        if (null == child || !(child instanceof WebView))
            return;

        WebView webView = (WebView) child;
        webView.loadUrl(url);
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        LogUtil.log("WebViewLayout", "onViewRemoved => child = " + child);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        LogUtil.log("WebViewLayout", "onViewAdded => child = " + child);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        View child = getChildAt(0);
        if (null == child || !(child instanceof WebView))
            return;

        WebView webView = (WebView) child;
        WebplusManager.push(webView);
        LogUtil.log("WebViewLayout", "onAttachedToWindow => webView = " + webView);
    }

    @Keep
    public void setOnWebStatusChangeListener(@NonNull WebViewPlus.OnWebStatusChangeListener listener) {

        if (null == listener)
            return;

        View child = getChildAt(0);
        if (null == child || !(child instanceof WebViewPlus))
            return;

        WebViewPlus webView = (WebViewPlus) child;
        webView.setOnWebStatusChangeListener(listener);
    }
}
