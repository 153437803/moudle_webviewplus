package lib.kalu.webviewplus.util;

import androidx.annotation.NonNull;

import java.net.URLEncoder;

/**
 * description:
 * created by kalu on 2021-04-21
 */
public class JavascriptUtil {

    public static final String encode(@NonNull String js) {

        if (null == js || js.length() == 0)
            return null;

        try {
            String javascript = URLEncoder.encode(js, "utf-8");
            return javascript;
        } catch (Exception e) {
            LogUtil.log("JavascriptUtil", "encode => message = " + e.getMessage(), e);
            return null;
        }
    }
}
