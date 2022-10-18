package lib.kalu.webviewplus;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import lib.kalu.webviewplus.core.WebViewCore;
import lib.kalu.webviewplus.manager.WebplusManager;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public class WebViewLayout extends FrameLayout {

    public WebViewLayout(@NonNull Context context) {
        super(context);
        com.tencent.smtt.sdk.WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        com.tencent.smtt.sdk.WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        com.tencent.smtt.sdk.WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        com.tencent.smtt.sdk.WebView webView = WebplusManager.pop(context.getApplicationContext());
        addView(webView);
    }

    public void onBackPressed(@NonNull Activity activity) {

        View child = getChildAt(0);
        if (null == child || !(child instanceof WebViewCore))
            return;

        WebViewCore webView = (WebViewCore) child;
        webView.onBackPressed(activity);
    }

    public void loadUrl(@NonNull String url) {

        if (null == url || url.length() == 0)
            return;

        View child = getChildAt(0);
        if (null == child || !(child instanceof com.tencent.smtt.sdk.WebView))
            return;

        com.tencent.smtt.sdk.WebView webView = (com.tencent.smtt.sdk.WebView) child;
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
        if (null == child || !(child instanceof com.tencent.smtt.sdk.WebView))
            return;

        com.tencent.smtt.sdk.WebView webView = (com.tencent.smtt.sdk.WebView) child;
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
