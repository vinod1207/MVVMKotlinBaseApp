package com.example.myapplication.apputils;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;

import com.example.myapplication.baseUi.AppConstant;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;


public class FileUtil {

    /**
     * This method is used to return a file.
     *
     * @return
     */
    public static File getFileToKeepImage(Context context) {

        if (isSDCARDMounted()) {
            String BASE_DIR = Objects.requireNonNull(context.getExternalCacheDir()).getPath();
            File f = new File(BASE_DIR, "tmp_avatar_"
                    + System.currentTimeMillis() + ".jpg");
            try {
                f.createNewFile();
            } catch (IOException e) {
            }
            return f;
        } else {
            return null;
        }
    }

    /**
     * This method is used to return a file.
     *
     * @return
     */
    public static File getFileToKeepPdf(Context context, String fileName) {

        if (isSDCARDMounted()) {
            String BASE_DIR = context.getExternalCacheDir().getPath();
            File f = new File(BASE_DIR, fileName + ".pdf");
            try {
                f.createNewFile();
            } catch (IOException e) {
            }
            return f;
        } else {
            return null;
        }
    }


    /**
     * This method is to return whether device SD card is maounted or not
     *
     * @return
     */
    public static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();

        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * This method is used to copy the crooped image
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
