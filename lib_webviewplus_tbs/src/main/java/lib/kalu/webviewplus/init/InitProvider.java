package lib.kalu.webviewplus.init;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

import lib.kalu.webviewplus.manager.WebplusManager;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description: 默认初始化Webview, 加速启动Webview
 * created by kalu on 2021-04-26
 */
@Keep
public class InitProvider extends ContentProvider {

    private static boolean mEnable = false;
    public static Context mWebplusContext;

    public static final void setEnableCache(boolean enable) {
        mEnable = enable;
    }

    @Override
    public boolean onCreate() {
        LogUtil.log("WebplusProvider", "onCreate =>");

        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(com.tencent.smtt.export.external.TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(com.tencent.smtt.export.external.TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        com.tencent.smtt.sdk.QbSdk.initTbsSettings(map);
        com.tencent.smtt.sdk.QbSdk.setDownloadWithoutWifi(true);
        // 1
        com.tencent.smtt.sdk.QbSdk.initX5Environment(getContext(), new com.tencent.smtt.sdk.QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            @Override
            public void onViewInitFinished(boolean isX5) {
            }
        });

        // 2
        if (mEnable) {
            mWebplusContext = getContext().getApplicationContext();
            WebplusManager.init();
        }
        return true;
    }

    @Override
    public void onLowMemory() {
        LogUtil.log("WebplusProvider", "onLowMemory =>");
        super.onLowMemory();
        // 调用系统API结束进程
        // android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
