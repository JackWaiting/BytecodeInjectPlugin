package com.sf.module.liblog.strategy;

import android.content.Context;

/**
 * 日志策略
 *
 * @author : hardy.zhou
 * @description
 * @date : 2019/10/12 14:26
 */
public interface LogStrategy {

    /**
     * 初始化日志模块
     */
    void init(Context mContext);

    /**
     * 写入Info信息到文件中
     */
    void wInfo(String info);

    /**
     * 写入Info信息到文件中
     */
    void wInfo(String info,String module,String fun);

    /**
     * 写入Error信息到文件中
     */
    void wError(String info);

    void wError(String info,String module,String fun);

    /**
     * 写入Error信息到文件中
     */
    void wError(Throwable e);

    void wError(Throwable e,String module,String fun);
}
