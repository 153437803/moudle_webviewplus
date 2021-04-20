package lib.kalu.webviewplus;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;

import lib.kalu.webviewplus.core.WebViewCore;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public class WebViewPlus extends WebViewCore {

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
}
