package com.sf.module.liblog.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.sf.module.liblog.BoxLogger;
import com.sf.module.liblog.strategy.LogConfigs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 配置信息, 包含文件静态配置和SpHelper动态配置
 * 
 */
public class Settings {

    private Context mContext;
    private static Object mLock = new Object();

    private Settings() {
    }

    private static Settings instance;

    public static Settings get(Context context) {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new Settings();
                    instance.init(context);
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        this.mContext = context.getApplicationContext();
        app = new AppSettings(context);
    }

    private AppSettings app;

    private static class AppSettings {
        // app版本
        private String apkVersionPrev = "sbznbox_";

        // 本地data目录
        private String dataPath = "/data/data/com.sf.module.edms";

        // 数据库存储路径
        private String dbPath = "databases";

        // sp文件名称
        private String spName = "hiboxSp";

        // 数据库名称
        private String dbName = "hibox.db";

        // 存储空间上的应用根目录
        private String storagePath; // /ExternalStorageDirectory/appName

        // 参照storagePath
        private String appName = "hibox";

        //sd卡智莱sf目录
        private String sfZhilaiPath;

        //sd卡sf目录下log日志
        private String sfLog = "sf";

        // 日志目录
        private String logPath = "log";

        // protobuf接口参数日志目录
        private String interfaceContentLogPath = "interface_log";

        // 数据统计目录
        private String statisticsPath = "statistics";

        // 格口目录
        private String tplPath = "tpl";

        // 更新apk目录
        private String apkPath = "apk";

        // 照片保存目录
        private String photoPath = "photo";

        //临时Hibox日志压缩文件存放目录
        private String tempHiboxLogPath = "tempHiboxLog";

        // 广告保存目录
        private String advertJsonPath = "advert/json";
        private String advertJsonMainPath = "advert/json/main";
        private String advertJsonScreenPath = "advert/json/screen";
        private String advertJsonTakePath = "advert/json/take";
        private String advertResourcePath = "advert/resource";

        private String cabinetAddressBigImagePath = "bigImage";
        private String cabinetAddressSmallImagePath = "smallImage";

        //各公司服务时间存放目录
        private String serveTimePath = "servetime";

        // 用户协议目录
        private String agreementPath = "agreement";

        // settings配置文件
        private String settingsFile = "settings.prop";

        AppSettings(Context context) {
            // 建立data目录
            FileUtil.mkDir(dataPath);
            // 建立storage目录
            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath().concat(File.separator)
                    .concat(appName);
            FileUtil.mkDir(storagePath);
        }
    }

    public String getApkVersion() {
        return app.apkVersionPrev + getAppVersion(mContext);
    }

    public static String getAppVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";

        }
    }


    public int getApkVersionCode() {
        return getAppVersionCode(mContext);
    }

    public static int getAppVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getDbPath() {
        return app.dataPath + File.separator + app.dbPath;
    }

    public String getDataPath() {
        return app.dataPath;
    }

    public String getSpName() {
        return app.spName;
    }

    public String getDbName() {
        return app.dbName;
    }

    public String getApkPath() {
        return app.storagePath + File.separator + app.apkPath;
    }

    public String getLogPath() {
        return app.storagePath + File.separator + app.logPath;
    }

    public String getInterfaceContentLogPath() {
        return app.storagePath + File.separator + app.interfaceContentLogPath;
    }

    public String getStatisticsPath() {
        return app.storagePath + File.separator + app.statisticsPath;
    }

    public String getTplPath() {
        return app.storagePath + File.separator + app.tplPath;
    }

//    public String getAdvsPath() {
//        return app.storagePath + File.separator + app.advsPath;
//    }

    public String getPhotoPath() {
        return app.storagePath + File.separator + app.photoPath;
    }

    public String getTempHiboxLogPath(){return app.storagePath + File.separator + app.tempHiboxLogPath;}

    public String getAgreementPath() {
        return app.storagePath + File.separator + app.agreementPath;
    }

    public String getadvertJsonPath() {
        return app.storagePath + File.separator + app.advertJsonPath;
    }

    public String getadvertJsonMainPath() {
        return app.storagePath + File.separator + app.advertJsonMainPath;
    }

    public String getadvertJsonScreenPath() {
        return app.storagePath + File.separator + app.advertJsonScreenPath;
    }

    public String getadvertJsonTakePath() {
        return app.storagePath + File.separator + app.advertJsonTakePath;
    }

    public String getadvertResourcePath() {
        return app.storagePath + File.separator + app.advertResourcePath;
    }
    public String getCabinetAddressBigImagePath() {
        return app.storagePath + File.separator + app.cabinetAddressBigImagePath;
    }

    public String getCabinetAddressSmallImagePath() {
        return app.storagePath + File.separator + app.cabinetAddressSmallImagePath;
    }

    public String getServeTimePath() {
        return app.storagePath + File.separator + app.serveTimePath;
    }

    public String getAgreementAssetPath() {
        return app.agreementPath;
    }

    public String getStoragePath() {
        return app.storagePath;
    }

    // 极速认证照片的存储路径
    public String getCertifyPath() {
        return app.storagePath + File.separator + "certify";
    }

    public String getSettingsFile(boolean isAbsolute) {
        if (isAbsolute) {
            return app.storagePath + File.separator + app.settingsFile;
        } else {
            return app.settingsFile;
        }
    }

    public void checkStorageAndCopyFromAsset(String fileName) {
        String absoluteName = app.storagePath + File.separator + fileName;
        File file = new File(absoluteName);
        if (!file.exists()) {
            FileUtil.copyAssets(mContext, fileName, absoluteName);
        }
    }

    /**********************
     * cfg属性 本地更改
     **********************/

    // 是否为调试模式
    boolean isDebug = false;
    // 格口报警时间(多久开始报警)
    int boxWarnTimeout;

    // 格口超时时间(超时后,业务异常)
    int boxFailTimeout;

    // 主界面跳转屏保界面的超时时间
    int uiJumpScreenTimeout;

    // 业务界面跳转主界面的超时时间
    int uiJumpMainTimeout;

    // 八达通service文件版本
    int octopusServiceVersion;

    public boolean isDebug() {
        return true;
//        return isDebug;
    }

    public int getOctopusServiceVersion() {
        return octopusServiceVersion;
    }

    public int getBoxWarnTimeout() {
        return boxWarnTimeout;
    }

    public int getBoxFailTimeout() {
        return boxFailTimeout;
    }

    public int getUiJumpScreenTimeout() {
        return uiJumpScreenTimeout;
    }

    public int getUiJumpMainTimeout() {
        return uiJumpMainTimeout;
    }

    /**
     * 从文件中读取配置信息
     */
    public boolean initFromFile(String fileName) {
        boolean ret = false;
        try {
            InputStream input = new FileInputStream(new File(fileName));
            Properties pro = new Properties();
            pro.load(input);
            ret = readProperties(pro);
            input.close();
        } catch (Exception e) {
            BoxLogger.wError(e, LogConfigs.Module.LOG, LogConfigs.Fun.LOG);
        }
        return ret;
    }

    public boolean readProperties(Properties prop) {
        String value;

        value = prop.getProperty("isDebug");
        isDebug = "true".equals(value);

        value = prop.getProperty("boxWarnTimeout");
        boxWarnTimeout = Integer.parseInt(value);

        value = prop.getProperty("boxFailTimeout");
        boxFailTimeout = Integer.parseInt(value);

        value = prop.getProperty("uiJumpScreenTimeout");
        uiJumpScreenTimeout = Integer.parseInt(value);

        value = prop.getProperty("uiJumpMainTimeout");
        uiJumpMainTimeout = Integer.parseInt(value);

        value = prop.getProperty("octopusServiceVersion");
        octopusServiceVersion = Integer.parseInt(value);

        return true;
    }
}