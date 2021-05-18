package lib.kalu.webviewplus.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Map;

import lib.kalu.webviewplus.R;
import lib.kalu.webviewplus.client.WebChromeClientPlus;
import lib.kalu.webviewplus.client.WebViewClientPlus;
import lib.kalu.webviewplus.impl.WebViewImpl;
import lib.kalu.webviewplus.util.FileUtil;
import lib.kalu.webviewplus.util.JavascriptUtil;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public class WebViewCore extends WebView implements WebViewImpl, Handler.Callback {

    public WebViewCore(Context context) {
        super(context);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs) {
        super(context, attrs);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    /**********/

    private final Handler mHandler = new Handler(WebViewCore.this);

    @Override
    public boolean handleMessage(@NonNull Message msg) {

        LogUtil.log("WebViewCore", "reload => run");

        if (msg.what != 1001 || msg.arg1 != 2002 || msg.arg2 != 3003) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        Object tag = getTag(R.id.id_webplus_assets_url_reload);
        if (null == tag) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        String url = tag.toString();
        if (!url.startsWith("http")) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        loadUrl(url);
        // setTag(R.id.id_webviewplus_targeturl, null);
        LogUtil.log("WebViewCore", "reload => succ");

        return false;
    }

    @JavascriptInterface
    @Override
    public void reload() {
        LogUtil.log("WebViewCore", "reload => start");
        Message message = Message.obtain(mHandler, 1001, 2002, 3003, null);
        message.sendToTarget();
    }

    @Override
    public boolean canGoForward() {
        return super.canGoForward();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void loadUrl(String url) {
        if (null == url || url.length() == 0)
            return;
        super.loadUrl(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (null == url || url.length() == 0)
            return;
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadData(String data, @Nullable String mimeType, @Nullable String encoding) {
        if (null == data || data.length() == 0)
            return;
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(@Nullable String baseUrl, String data, @Nullable String mimeType, @Nullable String encoding, @Nullable String historyUrl) {
        if (null == data || data.length() == 0)
            return;
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    /**********/

    @SuppressLint("JavascriptInterface")
    @Override
    public void initConfig(@NonNull WebView webView) {

        if (null != webView) {

            webView.setLongClickable(false);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });

            webView.removeJavascriptInterface(initJavascriptInterface());
            webView.addJavascriptInterface(WebViewCore.this, initJavascriptInterface());

            // fix h5网页视频有声音没图像
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            // 设置是否显示水平滚动条
            webView.setHorizontalScrollBarEnabled(false);
            // 设置垂直滚动条是否有叠加样式
            webView.setVerticalScrollbarOverlay(false);
            // 设置滚动条的样式
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

            WebSettings settings = webView.getSettings();
            if (null == settings)
                return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // 提高网页渲染的优先级
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            // 启用还H5的地理定位服务
            settings.setGeolocationEnabled(false);

            // 是否保存密码
            settings.setSavePassword(false);
            // 是否保存表单数据
            settings.setSaveFormData(false);
            // 开启 DOM storage API 功能
            settings.setDomStorageEnabled(true);
            // 开启 database storage API 功能
            settings.setDatabaseEnabled(false);
            // 开启 Application Caches 功能
            settings.setAppCacheEnabled(false);
            // 关闭webview中缓存
            // String path = getContext().getFilesDir().getAbsolutePath() + "/caweb"; // 无效果
            // settings.setAppCachePath(path); // 无效果
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            // 设置可以访问文件
            settings.setAllowFileAccess(true);
            // 设置编码格式
            settings.setDefaultTextEncodingName("utf-8");
            // 支持缩放
            settings.setSupportZoom(false);
            // 设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上，默认true，展现在控件上
            settings.setDisplayZoomControls(false);
            settings.setAllowContentAccess(true);
            settings.setAllowFileAccess(true);
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            // 设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用，默认是false，不使用内置变焦机制
            settings.setBuiltInZoomControls(false);
            // 设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制
            settings.setAllowContentAccess(false);
            // 设置WebView是否使用预览模式加载界面, 缩放至屏幕的大小
            settings.setLoadWithOverviewMode(true);
            // 设置WebView中加载页面字体变焦百分比, 默认100, 整型数
            settings.setTextZoom(100);
            // 将图片调整到适合webview的大小
            settings.setUseWideViewPort(true);
            // 设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.setMediaPlaybackRequiresUserGesture(false);
            }
            // 支持Javascript
            settings.setJavaScriptEnabled(true);
            //设置webview支持插件
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setSupportMultipleWindows(true);// 新加
            // 支持通过JS打开新窗口
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            // 设置WebView是否以http、https方式访问从网络加载图片资源，默认false
            settings.setBlockNetworkImage(false);
            // 不立即加载图片, 等页面加载完成后设置加载图片
            settings.setLoadsImagesAutomatically(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
            // 处理http 和 https 图片混合的问题
            // settings.setMixedContentMode(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE : WebSettings.LOAD_DEFAULT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }
        }
    }

    @Override
    public void initBackground(@NonNull WebView webView) {

        if (null != webView) {
            setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            setBackgroundResource(android.R.color.transparent);
        }
    }

    @Override
    public void initWebViewClient(@NonNull WebView webView) {

        if (null != webView) {

            webView.setWebViewClient(new WebViewClientPlus() {

            });
        }
    }

    @Override
    public void initWebChromeClient(@NonNull WebView webView) {

        if (null != webView) {

            webView.setWebChromeClient(new WebChromeClientPlus() {

            });
        }
    }

    @Override
    public void onPageStarted(@NonNull WebView view, @NonNull String url) {
        LogUtil.log("WebViewCore", "onPageStarted => url = " + url);
    }

    @Override
    public void onPageFinished(@NonNull WebView view, @NonNull String url) {
        LogUtil.log("WebViewCore", "onPageFinished => url = " + url);
    }

    @Override
    public boolean onJsTimeout() {
        LogUtil.log("WebViewCore", "onJsTimeout =>");
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsBeforeUnload => url = " + url);
        return true;
    }

    @Override
    public boolean onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsAlert => url = " + url);
        return true;
    }

    @Override
    public boolean onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsConfirm => url = " + url);
        return true;
    }

    @Override
    public boolean onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result) {
        LogUtil.log("WebViewCore", "onJsPrompt => url = " + url);
        return true;
    }

    @Override
    public void onBackPressed(@NonNull Activity activity, boolean tryAgin) {

        if (null == activity)
            return;

        // 判断initAssetDefaultInitResourceName
        boolean canGoBack = canGoBack();
        String initUrl = null;
        if (null != getTag(R.id.id_webplus_assets_url_init)) {
            initUrl = getTag(R.id.id_webplus_assets_url_init).toString();
        }
        String targetUrl = getUrl();
        String resourceName = initAssetDefaultInitResourceName();
        LogUtil.log("WebViewCore", "onBackPressed => tryAgin = " + tryAgin + ", canGoBack = " + canGoBack + ", targetUrl = " + targetUrl + ", initUrl = " + initUrl + ", resourceName = " + resourceName);

        if (tryAgin && null != initUrl && initUrl.length() > 0 && initUrl.startsWith("file:///android_asset/") && initUrl.endsWith(resourceName)) {
            activity.finish();
        }
        // 回退上一页
        else if (canGoBack()) {
            goBack();
            onBackPressed(activity, true);
        }
        // 默认
        else if (!tryAgin) {
            activity.finish();
        }
    }

    @Override
    public void loadJavascriptAssets(@NonNull String fliename) {
        Context context = getContext().getApplicationContext();
        String jstring = FileUtil.readAssets(context, fliename);
        loadJavascriptString(jstring);
    }

    @Override
    public void loadJavascriptString(@NonNull String jstring) {
        String javascript = JavascriptUtil.encode(jstring);
        loadDataWithBaseURL(null, javascript, "application/javascript", "utf-8", null);
        // evaluateJavascript(javascript, callbacck);
    }

    @Override
    public void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress) {
    }
}
