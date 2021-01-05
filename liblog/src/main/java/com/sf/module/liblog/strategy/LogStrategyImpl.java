package com.sf.module.liblog.strategy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sf.module.liblog.utils.FileTimeUtil;
import com.sf.module.liblog.utils.FileUtil;
import com.sf.module.liblog.utils.Settings;
import com.sf.module.liblog.utils.TimeUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 调试日志打印, 写文件.
 */
public class LogStrategyImpl implements LogStrategy {

    private static final String TAG = LogStrategyImpl.class.getSimpleName();
    private Context mContext;
    private LinkedBlockingQueue<String> queue;
    private WriteRunnable writeRunnable;

    /** 片段间隔，单位小时，24%LOG_SEGMENT=0. */
    private static final int LOG_SEGMENT = 1;

    /**
     * 写入信息到文件中.
     *
     * @param infos 信息数组
     */
    private synchronized void writeEx(String[] infos) {
        File logPath = new File(Settings.get(mContext).getLogPath());
        // 判断目录是否存在，不存在就创建
        if (!logPath.exists()) {
            boolean isMkdirSuccess = logPath.mkdirs();
            if (!isMkdirSuccess) {
                return; // 日志目录创建失败, 退出
            }
        }
        // 判断文件是否存在
        String timeSegment = TimeUtil.getCurrentDay() + "_" + FileTimeUtil.getHourSegment(); // like 2015-09-22_0001
        String logFilePath = logPath.getPath().concat(File.separator).concat(timeSegment).concat(".log");
        File log = new File(logFilePath);
        if (!log.exists()) {
            try {
                boolean isCreateSuccess = log.createNewFile();
                if (!isCreateSuccess) {
                    return; // 文件创建失败, 退出
                }
                saveLastZip();
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }

        // 写入文件
        if (log.exists()) {
            try {
                FileWriter fw = new FileWriter(log, true); // 追加模式
                for (String info : infos) {
                    fw.write(info);
                }
                fw.close();
            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
    }

    private void saveLastZip() {
        String hourSegment = FileTimeUtil.getHourSegment();
        String segment = (24 - LogStrategyImpl.LOG_SEGMENT) + "00";
        int prevDay = 0;
        // 寻找最近三天的日志分片
        for (int i = 0; i < 72; i++) {
            hourSegment = FileTimeUtil.getPreHourSegment(hourSegment); //获取之前的时间片段
            String theDay;
            if (TextUtils.equals(segment, hourSegment)) { //时间临界点判断
                prevDay = prevDay - 1;
                theDay = TimeUtil.addDay(prevDay);
            } else {
                theDay = TimeUtil.addDay(prevDay);
            }

            String prevDate = theDay +"_"+ hourSegment;
            String fileName = prevDate + ".log";
            String filePath = Settings.get(mContext).getLogPath() + File.separator + fileName; // 前一天日志文件的完整存储路径
            File log = new File(filePath);

            // 要压缩的目的目录和名字是"log父目录/yyyy-mm-dd_hhhh.zip"
            String zipFilePath = log.getParent() + File.separator + prevDate + ".zip";
            File zip = new File(zipFilePath);

            if (log.exists()) {
                // zip不存在
                if (!zip.exists()) {
                    try {
                        FileUtil.zipFolder(log.getPath(), zipFilePath);
                        wInfo("日志文件压缩-->" + zipFilePath);
                    } catch (Exception e) {
                        wError("文件压缩异常：" + e.getMessage());
                    }
                }

                // log存在,不再遍历
                break;
            }
        }
    }

    @Override
    public void init(Context mContext) {

        this.mContext = mContext;
        queue = new LinkedBlockingQueue<>();
        writeRunnable = new WriteRunnable();
        new Thread(writeRunnable, "LogThread@" + Integer.toHexString(writeRunnable.hashCode())).start();
    }

    private class WriteRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    String info = queue.take();
                    int size = queue.size();
                    String[] infos = new String[size + 1];
                    infos[0] = info;
                    int i = 1;
                    while (size > 0) {
                        infos[i] = queue.take();
                        i++;
                        size--;
                    }
                    writeEx(infos);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    private void write(String info, String level) {
        write(info,level,"未升级","未升级");
    }


    private void write(String info, String level,String module,String fun) {

        /*if (Settings.get(mContext).isDebug()) {
            Log.i("fcbox-"+level, info);
        }*/
        try {
            int apkVersionCode = Settings.get(mContext).getApkVersionCode();
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[3];
            String method = stackTraceElement.getMethodName();
            String clazz = stackTraceElement.getFileName();
            int line = stackTraceElement.getLineNumber();

            Log.i("MyTestLog", "\n" + "[" + TimeUtil.getCurrentTime() + " " + level + " " + apkVersionCode
                    + " " + clazz + "$" + line + " " + method + "]" + "[" + module + "]" + "[" + fun + "]\n" + info + "\n");

            queue.add("\n" + "[" + TimeUtil.getCurrentTime() + " " + level + " " + apkVersionCode
                    + " " + clazz + "$" + line + " " + method + "]" + "[" + module + "]" + "[" + fun + "]\n" + info + "\n");

        } catch (Exception e){
            int apkVersionCode = Settings.get(mContext).getApkVersionCode();
            queue.add("\n" + "[" + TimeUtil.getCurrentTime() + " " + level + " " + apkVersionCode+" [" + module + "]" + "[" + fun + "]\n" + "LogStrategyImpl write写日志时报错:e:"+e.getMessage() + "\n");
        }
    }

    /**
     * 写入Info信息到文件中
     */
    @Override
    public void wInfo(String info) {
        write(info, "INFO");
    }

    @Override
    public void wInfo(String info, String module, String fun) {
        write(info, "INFO",module,fun);
    }

    /**
     * 写入Error信息到文件中
     */
    @Override
    public void wError(String info) {
        write(info, "ERROR");
    }

    @Override
    public void wError(String info, String module, String fun) {
        write(info, "ERROR",module,fun);
    }

    /**
     * 写入Error信息到文件中
     */
    @Override
    public void wError(Throwable e) {
        write(Log.getStackTraceString(e), "ERROR");
    }

    @Override
    public void wError(Throwable e, String module, String fun) {
        write(Log.getStackTraceString(e), "ERROR",module,fun);
    }

}




