package lib.kalu.webviewplus.init;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.webviewplus.manager.WebplusManager;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description: 默认初始化Webview, 加速启动Webview
 * created by kalu on 2021-04-26
 */
@Keep
public class WebplusProvider extends ContentProvider {

    public static Context mWebplusContext;

    @Override
    public boolean onCreate() {
        LogUtil.log("WebplusProvider", "onCreate =>");
        mWebplusContext = getContext().getApplicationContext();
        WebplusManager.init();
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
