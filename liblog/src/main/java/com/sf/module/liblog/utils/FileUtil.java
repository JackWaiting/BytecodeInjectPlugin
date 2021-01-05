package com.sf.module.liblog.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.sf.module.liblog.BoxLogger;
import com.sf.module.liblog.strategy.LogConfigs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static boolean mkDir(String path) {
        File dir = new File(path);
        if (!dir.exists())
            return dir.mkdirs();
        return true;
    }

    public static void rmDir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
            } else {
                for (File f : childFile) {
                    rmFile(f);
                }
            }
        }

    }

    /**
     * 删除文件或目录(包括底下所有文件)
     * @param fileName
     */
    public static void rmFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            rmFile(file);
        }
    }

    public static void rmFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                rmFile(f);
            }
            file.delete();
        }
    }

    public static void copyAssetsDir(Context context, String ast, String desPath) {
        try {
            String subs[] = context.getAssets().list(ast);
            if (subs != null && subs.length > 0) {// 如果是目录
                mkDir(desPath + File.separator + ast);
                for (String sub : subs) {
                    copyAssetsDir(context, ast + File.separator + sub, desPath);
                }
            } else {// 如果是文件
                boolean isFile = true;
                try {
                    InputStream input = context.getAssets().open(ast);
                    input.close();
                } catch (FileNotFoundException e) {
                    isFile = false;
                }
                if (isFile) {
                    File file = new File(desPath + File.separator + ast);
                    if (!file.exists()) {
                        copyAssets(context, ast, desPath + File.separator + ast);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制asset目录下的文件到系统路径下
     */
    public static boolean copyAssets(Context context, String ast, String des) {
        InputStream input = null;
        OutputStream out = null;

        try {
            AssetManager manager = context.getAssets();
            input = manager.open(ast);
            out = new FileOutputStream(des);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            input.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取Assets下目录的文件
     */
    public static String readAssetsFileStr(Context context, String fileName) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);

            while (true) {
                String line = bufReader.readLine();
                if (line == null)
                    break;

                sb.append(line);
                sb.append("\n");
            }
            inputReader.close();
            bufReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 写入文件
     */
    public static boolean writeFile(String fileName, String content) {
        boolean result = false;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }else{
                if(!file.isFile()){
                    file.delete();
                    file.createNewFile();
                }
            }
            writeFile(file, content);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     * 写入文件
//     */
//    public static boolean writeFile(File file, String content) {
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            writeFile(file, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * 写入文件
     */
    public static void writeFile(File file, String content) throws IOException {
        FileWriter fw = new FileWriter(file, false);// true表示添加，false表示覆盖
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        fw.write(sb.toString());
        fw.close();
    }

    /**
     * 读取字符串文件
     *
     * @throws IOException
     */
    public static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        return readFile(file);
    }

    /**
     * 读取字符串文件
     *
     * @throws IOException
     */
    public static String readFile(File fileName) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
        while (true) {
            String line = bufReader.readLine();
            if (line == null)
                break;

            sb.append(line);
            sb.append("\n");
        }
        bufReader.close();
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 查找文件是否存在
     */
    public static boolean exists(String dir, String name) {
        File file = new File(dir, name);
        return file.exists();
    }

    /**
     * 重命名文件名
     */
    public static boolean renameTo(String dir, String oldName, String newName) {
        File file = new File(dir, oldName);
        return file.renameTo(new File(dir, newName));
    }

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

    }// end of func

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
            inputStream.close();
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
            }// end of for

        }// end of if

    }// end of func

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

    public static boolean fileExist(File file, String filename) {
        boolean result = false;
        String[] files = file.list();
        if (files != null && files.length > 0) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].equals(filename) && files[j].length() > 0) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }


    /**
     * Instances should NOT be constructed in standard programming.
     */
    public FileUtil() {
        super();
    }

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;
    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_KB * 4;

    public static boolean copyDir(File srcDir, File destDir) {
        boolean result = false;
        try {
            copyDirectory(srcDir, destDir);
            result = true;
        } catch (IOException e) {
            BoxLogger.wError(e, LogConfigs.Module.LOG, LogConfigs.Fun.LOG);
        }
        return result;
    }

    /**
     * Copies a whole directory to a new location preserving the file dates.
     * <p/>
     * This method copies the specified directory and all its child directories
     * and files to the specified destination. The destination is the new
     * location and name of the directory.
     * <p/>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p/>
     * <strong>Note:</strong> This method tries to preserve the files' last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that those operations will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param srcDir  an existing directory to copy, must not be {@code null}
     * @param destDir the new directory, must not be {@code null}
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during copying
     * @since 1.1
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    /**
     * Copies a whole directory to a new location.
     * <p/>
     * This method copies the contents of the specified source directory to
     * within the specified destination directory.
     * <p/>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p/>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the files' last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that
     * those operations will succeed. If the modification operation fails, no
     * indication is provided.
     *
     * @param srcDir           an existing directory to copy, must not be {@code null}
     * @param destDir          the new directory, must not be {@code null}
     * @param preserveFileDate true if the file date of the copy should be the same as the
     *                         original
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during copying
     * @since 1.1
     */
    public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    /**
     * Copies a filtered directory to a new location.
     * <p/>
     * This method copies the contents of the specified source directory to
     * within the specified destination directory.
     * <p/>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p/>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the files' last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that
     * those operations will succeed. If the modification operation fails, no
     * indication is provided.
     * <p/>
     * <h4>Example: Copy directories only</h4>
     * <p/>
     * <pre>
     * // only copy the directory structure
     * FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
     * </pre>
     * <p/>
     * <h4>Example: Copy directories and txt files</h4>
     * <p/>
     * <pre>
     * // Create a filter for &quot;.txt&quot; files
     * IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(&quot;.txt&quot;);
     * IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
     *
     * // Create a filter for either directories or &quot;.txt&quot; files
     * FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
     *
     * // Copy using the filter
     * FileUtils.copyDirectory(srcDir, destDir, filter, false);
     * </pre>
     *
     * @param srcDir           an existing directory to copy, must not be {@code null}
     * @param destDir          the new directory, must not be {@code null}
     * @param filter           the filter to apply, null means copy all directories and files
     * @param preserveFileDate true if the file date of the copy should be the same as the
     *                         original
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during copying
     * @since 1.4
     */
    public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate)
            throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        // Cater for destination being directory within the source directory
        // (see IO-141)
        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<String>(srcFiles.length);
                for (File srcFile : srcFiles) {
                    File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
    }

    /**
     * Internal copy directory method.
     *
     * @param srcDir           the validated source directory, must not be {@code null}
     * @param destDir          the validated destination directory, must not be {@code null}
     * @param filter           the filter to apply, null means copy all directories and files
     * @param preserveFileDate whether to preserve the file date
     * @param exclusionList    List of files and directories to exclude from the copy, may be
     *                         null
     * @throws IOException if an error occurs
     * @since 1.1
     */
    private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate,
                                        List<String> exclusionList) throws IOException {
        // recurse
        File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) { // null if abstract pathname does not denote a
            // directory, or if an I/O error occurs
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (!destDir.mkdirs() && !destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
        }
        if (!destDir.canWrite()) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (File srcFile : srcFiles) {
            File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                } else {
                    doCopyFile(srcFile, dstFile, preserveFileDate);
                }
            }
        }

        // Do this last, as the above has probably affected directory metadata
        if (preserveFileDate) {
            destDir.setLastModified(srcDir.lastModified());
        }
    }

    /**
     * Internal copy file method.
     *
     * @param srcFile          the validated source file, must not be {@code null}
     * @param destFile         the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException if an error occurs
     */
    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fis);
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }


    public boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            } else {
                isok = false;
            }
        } catch (Exception e) {
            // System.out.println("复制单个文件操作出错");
            // e.printStackTrace();
            isok = false;
        }
        return isok;

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            isok = false;
        }
        return isok;
    }

    public static void copyDB(Context context) {

    }

    /**
     * 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}

