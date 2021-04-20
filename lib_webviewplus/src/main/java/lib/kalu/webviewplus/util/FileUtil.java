package lib.kalu.webviewplus.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.FileNameMap;
import java.net.URLConnection;

public class FileUtil {

    public static final String getMimeType(@NonNull String filepath) {

        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimetype = fileNameMap.getContentTypeFor(filepath);
        Log.d("FileUtil", "getMimeType => mimetype = " + mimetype);
        return mimetype;
    }

    public static boolean writeToLocal(@NonNull String filepath, @NonNull byte[] bytes) {

        boolean result = false;
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileOutputStream = new FileOutputStream(filepath);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            String value = new String(bytes);
            bufferedWriter.write(value);
            bufferedWriter.flush();
            result = true;

        } catch (Exception e) {

            result = false;
        } finally {

            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }

            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (Exception e) {
                }
            }
        }

        return result;
    }
}
