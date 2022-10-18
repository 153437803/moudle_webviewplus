package lib.kalu.webviewplus.util;

import androidx.annotation.NonNull;

import java.net.URLEncoder;

/**
 * description: js操作
 * created by kalu on 2021-04-21
 */
public class JavascriptUtil {

    /**
     * 解决url可能存在的特殊字符, 统一二次编码容错
     *
     * @param js
     * @return
     */
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
