package lib.kalu.webviewplus.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.webviewplus.BuildConfig;

/**
 * description: log
 * created by kalu on 2021-04-21
 */
public class LogUtil {

    public static void log(@NonNull String tag, @NonNull String msg) {

        log(tag, msg, null);
    }

    public static void log(@NonNull String tag, @NonNull String msg, @Nullable Throwable tr) {

        if (BuildConfig.DEBUG && null != msg && msg.length() > 0) {
            Log.d(tag, msg, tr);
        }
    }
}
