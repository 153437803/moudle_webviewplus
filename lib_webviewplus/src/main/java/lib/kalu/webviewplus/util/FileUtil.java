package lib.kalu.webviewplus.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.FileNameMap;
import java.net.URLConnection;

public class FileUtil {

    public static final String getMimeType(@NonNull String filepath) {

        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimetype = fileNameMap.getContentTypeFor(filepath);
        LogUtil.log("FileUtil", "getMimeType => mimetype = " + mimetype);
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
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
