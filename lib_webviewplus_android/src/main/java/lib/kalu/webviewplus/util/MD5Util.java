package lib.kalu.webviewplus.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * description: md5
 * created by kalu on 2021-04-20
 */
public final class MD5Util {

    public static String getMD5Str(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(str.getBytes("utf-8"));
            //16是表示转换为16进制数
            String md5Str = new BigInteger(1, digest).toString(16);
            return md5Str;
        } catch (Exception e) {
            return null;
        }
    }
}