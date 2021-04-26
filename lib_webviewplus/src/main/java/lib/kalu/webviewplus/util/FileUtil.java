package lib.kalu.webviewplus.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {

    public static final void deleteFile(@NonNull File file) {

        if (null == file || !file.exists() || file.isDirectory())
            return;
        file.delete();
    }

    public static final String getMimeType(@NonNull String filepath) {

        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimetype = fileNameMap.getContentTypeFor(filepath);
        LogUtil.log("FileUtil", "getMimeType => mimetype = " + mimetype);
        return mimetype;
    }

    public static final boolean writeToLocal(@NonNull String fileUrl, @NonNull String finePath) {

        try {

            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            //下载后的文件名
            File file = new File(finePath);
            if (file.exists()) {
                file.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(file.getAbsolutePath());
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
            LogUtil.log("FileUtil", "writeToLocal => status = true, fileUrl = " + fileUrl + ", filePath = " + file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            LogUtil.log("FileUtil", "writeToLocal => status = false, fileUrl = " + fileUrl + ", filePath = null");
            return false;
        }
    }

    public static String readAssets(@NonNull Context context, @NonNull String fileName) {

        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            LogUtil.log("FileUtil", "readAssets => message = " + e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    LogUtil.log("FileUtil", "readAssets => message = " + e.getMessage(), e);
                }
            }
        }
        return null;
    }
}
