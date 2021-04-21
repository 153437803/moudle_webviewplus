package lib.kalu.webviewplus.core;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import lib.kalu.webviewplus.client.WebChromeClientPlus;
import lib.kalu.webviewplus.client.WebViewClientPlus;
import lib.kalu.webviewplus.impl.WebViewImpl;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public class WebViewCore extends WebView implements WebViewImpl {

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

    @Override
    public void initConfig(@NonNull WebView webView) {

        if (null != webView) {

            // fix h5网页视频有声音没图像
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            WebSettings settings = webView.getSettings();
            if (null == settings)
                return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

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

            // fix h5网页视频有声音没图像
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
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
    public void onProgressChanged(@NonNull WebView view, int newProgress) {
        LogUtil.log("WebViewCore", "onProgressChanged => newProgress = " + newProgress);
    }
}
