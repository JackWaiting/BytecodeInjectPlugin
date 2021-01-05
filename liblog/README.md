### 日志打印存储组件
1 调用初始化方法
  // appName 项目名字
  // isDebug 是否是测试环境 是则打印日志到控制台
  BaseLog.getInstance().(String appName, boolean isDebug)

2 把日志写入文件
   BaseLog.wInfo(String info)
   BaseLog.wError(String info)
   BaseLog.wError(Throwable e)

3  压缩文件
   // srcFilePath 要压缩的文件/文件夹名字
   // zipFilePath 指定压缩的目的和名字
   LogSettings.zipFolder(String srcFilePath, String zipFilePath)


#### 
#### 