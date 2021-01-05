package com.sf.module.liblog;

import android.content.Context;

import com.sf.module.liblog.strategy.LogStrategy;
import com.sf.module.liblog.strategy.LogStrategyImpl;

/**
 * 调试日志打印, 写文件.
 * Created by 01383436
 * on 2019.01.29 14:36
 */
public class BoxLogger {

    protected static final String TAG = BoxLogger.class.getSimpleName();

    /**
     * 日志策略
     */
    private static LogStrategy logStrategy;

    private static BoxLogger instance;

    public static BoxLogger getInstance(Context context) {
        if(instance == null){
            instance = new BoxLogger(context);
        }
        return instance;
    }

    /**
     * 初始化日志
     */
    private BoxLogger(Context context){
        logStrategy = new LogStrategyImpl();
        logStrategy.init(context.getApplicationContext());
    }

    /**
     * 写入Info信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    @Deprecated
    public static void wInfo(String info) {
        if (null != logStrategy) {
            logStrategy.wInfo(info);
        }
    }

    /**
     * 写入Error信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    @Deprecated
    public static void wError(String info) {
        if (null != logStrategy) {
            logStrategy.wError(info);
        }
    }

    /**
     * 写入Error信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    @Deprecated
    public static void wError(Throwable e) {
        if (null != logStrategy) {
            logStrategy.wError(e);
        }
    }

    /**
     * 写入Info信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    public static void wInfo(String info,String module,String fun) {
        if (null != logStrategy) {
            logStrategy.wInfo(info,module,fun);
        }
    }

    /**
     * 写入Error信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    public static void wError(String info,String module,String fun) {
        if (null != logStrategy) {
            logStrategy.wError(info,module,fun);
        }
    }

    /**
     * 写入Error信息到文件中
     * 推荐按功能模块继承，采用TAG标识
     */
    public static void wError(Throwable e,String module,String fun) {
        if (null != logStrategy) {
            logStrategy.wError(e,module,fun);
        }
    }

}




