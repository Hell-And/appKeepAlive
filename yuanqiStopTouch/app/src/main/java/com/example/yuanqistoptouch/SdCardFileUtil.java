package com.example.yuanqistoptouch;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;


/**
 * 外部存储空间，读写操作封装,其存储位置:sd card/yq/tsa/file/assistant/
 */
public class SdCardFileUtil {

    private static final String DIR_DEFAULT_PATH = "/sys/exgpio/hub_usb5_host0_vcc";
//    private static final String DIR_DEFAULT_PATH = "/yq/tsa/file/assistant/hub_usb5_host0_vcc";

    /**
     * 小文件的写操作
     */
    public static String save(@NonNull String strFileContent) {
//        if (TextUtils.isEmpty(strFileName) || TextUtils.isEmpty(strFileContent)) {
//            return false;
//        }
//        if (strFileName.lastIndexOf(".") == -1) {
//            //如果文件名没有后缀名，添加".txt"
//            strFileName += ".txt";
//        }


        //创建目录如不存在
        File fileDir = new File(DIR_DEFAULT_PATH);
        boolean isMakeDirOrFile = true;
//        if (fileDir.exists() && fileDir.isDirectory()) {
//            isMakeDirOrFile = true;
//        } else if (!fileDir.exists()) {
//            isMakeDirOrFile = fileDir.mkdirs();
//        }
        //创建文件如不存在
//        File fileFile = new File(fileDir, strFileName);
//        if (!fileFile.exists()) {
//            try {
//                isMakeDirOrFile = fileFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//                isMakeDirOrFile = false;
//            }
//        }


        //写文件
        if (isMakeDirOrFile) {
            FileOutputStream fileOutputStream = null;
            boolean isSuccess = false;
            String result="成功";
            try {
//                if (fileDir.isDirectory()) {
//                    Log.d(TAG, "save: " + fileDir.listFiles().length);
//                    for (File file : fileDir.listFiles()) {
//                        Log.d(TAG, "save: " + file.getAbsolutePath());
//                    }
//                }

                fileOutputStream = new FileOutputStream(fileDir);
                fileOutputStream.write(strFileContent.getBytes());
                fileOutputStream.flush();
                isSuccess = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result=e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                result=e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                result=e.getMessage();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        result=e.getMessage();
                    }
                }
            }

            return result;
        } else {
            return "文件不存在";
        }
    }

    /**
     * 小文件的读操作
     */
    public static String load(@NonNull String strFileName) {
        if (TextUtils.isEmpty(strFileName)) {
            return "";
        }
        if (strFileName.lastIndexOf(".") == -1) {
            //如果文件名没有后缀名，添加".txt"
            strFileName += ".txt";
        }


        //读取文件不存在
        File fileFile = new File(Environment.getExternalStorageDirectory() + DIR_DEFAULT_PATH + strFileName);
        if (!fileFile.exists() || fileFile.isDirectory()) {
            return "";
        }

        //读文件
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        String strRet = "";
        try {
            fileInputStream = new FileInputStream(fileFile);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            strRet = byteArrayOutputStream.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strRet;
    }

}
