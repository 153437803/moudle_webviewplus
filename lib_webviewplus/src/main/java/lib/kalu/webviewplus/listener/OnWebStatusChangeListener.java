package lib.kalu.webviewplus.listener;

import android.webkit.WebView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-22
 */
@Keep
public interface OnWebStatusChangeListener {

    void onPageStarted(@NonNull WebView view, @NonNull String url);

    void onPageFinished(@NonNull WebView view, @NonNull String url);

    void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress);
}
