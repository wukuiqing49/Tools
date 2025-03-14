package com.wkq.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常捕捉 处理数据上传
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private Context mContext;
    private volatile static CrashHandler mCrashHandler;
    private  long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            synchronized (CrashHandler.class) {
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandler();
                }
            }
        }
        return mCrashHandler;
    }

    public void init(Context context,long maxSize) {
        MAX_FILE_SIZE=maxSize;
        mContext = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpExceptionToFile(e);
        if (mDefaultHandler != null) {
            //系统默认的异常处理器来处理,否则由自己来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理文件写入类型
     * @param e
     */
    private void dumpExceptionToFile(Throwable e) {
        try {
            long timeMillis = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
            File file = new File(getFolder("crash/log/"), "log" + ".trace");
            checkAndClearFile(mContext,file);

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.println(time);
            pw.print("App Version: ");
            pw.print(pi.versionName);
            pw.print('_');
            pw.println(pi.versionCode);

            // android版本号
            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);

            // 手机制造商
            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);

            // 手机型号
            pw.print("Model: ");
            pw.println(Build.MODEL);

            // cpu架构
            pw.print("CPU ABI: ");
            pw.println(Build.CPU_ABI);
            e.printStackTrace(pw);
            pw.close();
        } catch (Exception ex) {
            Log.e(TAG, "dump crash info error");
        }

    }


    //可以根据自己需求来,比如获取手机厂商、型号、系统版本、内存大小等等
    public void setLog(String log) {
        try {
            long timeMillis = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
            File file = new File(getFolder("crash/log/"), "crash.log");
            if (!file.exists()) file.createNewFile();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(time);
            pw.println(log + "\n" + "\n" + "\n");
            pw.close();
        } catch (IOException ex) {
            Log.e(TAG, "dump crash info error");
        }

    }


    public static void writeLogcatToFile(Context context) {
        try {
            // 获取应用的内部存储目录
            File logFile = new File(context.getFilesDir(), "logcat.log");
            // 创建一个 BufferedWriter 用于写入日志
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            // 执行 logcat 命令
            Process process = Runtime.getRuntime().exec("logcat -d");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // 将日志信息写入文件
                writer.write(line);
                writer.newLine();
            }
            // 关闭流
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取子目录
     */
    private File getFolder(String dirName) {
        File appDir;
        if (isSDCardAvailable()) {
            appDir = mContext.getExternalFilesDir(null);
        } else {
            return null;
        }
        File file = new File(appDir, dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 检查文件大小并在超过 10MB 时清空文件
     *
     * @param context  上下文对象
     * @param file 文件名
     */
    public void checkAndClearFile(Context context, File file) {

       try {
            if (file.exists()) {
                long fileSize = file.length();
                if (fileSize > MAX_FILE_SIZE) {
                    //清空原先数据
                    FileOutputStream fos = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
                    fos.close();
                }
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提供方法上传异常信息到服务器
     *
     * @param log log日志文件地址
     */
    private void uploadExceptionToServer(File log) {
        //

    }

    /**
     * SD卡是否可用
     */
    private static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}