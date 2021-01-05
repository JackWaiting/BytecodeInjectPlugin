package com.sf.module.liblog.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 日志目录与文件压缩
 * Created by 01383436
 * on 2019.01.29 14:36
 */

public class LogUtils {
    /**
     * 压缩文件,文件夹
     *
     * @param srcFilePath 要压缩的文件/文件夹名字
     * @param zipFilePath 指定压缩的目的和名字
     * @throws Exception
     */
    public static void zipFolder(String srcFilePath, String zipFilePath) throws Exception {
        // 创建Zip包
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFilePath));
        // 打开要输出的文件
        File file = new File(srcFilePath);
        // 压缩
        zipFiles(file.getParent() + File.separator, file.getName(), outZip);
        // 完成,关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     *
     * @param folderPath
     * @param filePath
     * @param zipOut
     * @throws Exception
     */
    private static void zipFiles(String folderPath, String filePath, ZipOutputStream zipOut) throws Exception {
        if (zipOut == null) {
            return;
        }

        File file = new File(folderPath + filePath);

        // 判断是不是文件
        if (file.isFile()) {

            ZipEntry zipEntry = new ZipEntry(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            zipOut.putNextEntry(zipEntry);

            int len;
            byte[] buffer = new byte[4096];

            while ((len = inputStream.read(buffer)) != -1) {
                zipOut.write(buffer, 0, len);
            }

            zipOut.closeEntry();
        } else {
            // 文件夹的方式,获取文件夹下的子文件
            String fileList[] = file.list();
            // 如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(filePath + File.separator);
                zipOut.putNextEntry(zipEntry);
                zipOut.closeEntry();
            }
            // 如果有子文件, 遍历子文件
            for (int i = 0; i < fileList.length; i++) {
                zipFiles(folderPath, filePath + File.separator + fileList[i], zipOut);
            }
        }
    }

    /**
     * 压缩多个文件
     * @param files 要压缩的文件s
     * @param zipFilePath 指定压缩的目的和名字
     */
    public static void zipFiles(List<File> files, String zipFilePath) throws Exception {
        // 创建Zip包
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFilePath));
        InputStream input = null;
        for (int i = 0; i < files.size(); ++i) {
            input = new FileInputStream(files.get(i));
            outZip.putNextEntry(new ZipEntry(files.get(i).getName()));
            int temp = 0;
            while ((temp = input.read()) != -1) {
                outZip.write(temp);
            }
            input.close();
        }
        // 完成,关闭
        outZip.finish();
        outZip.close();

    }
}
