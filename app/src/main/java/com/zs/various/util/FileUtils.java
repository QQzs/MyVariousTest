/*
 * 深圳市有信网络技术有限公司
 * Copyright (c) 2016 All Rights Reserved.
 */

package com.zs.various.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class FileUtils {

    public static void deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            deleteFile(new File(path));
        }
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFileEx(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                deleteFileSafely(file);
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    deleteFileSafely(file);
                    return;
                }
                for (File f : childFile) {
                    deleteFileEx(f);
                }
                deleteFileSafely(file);
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件夹下的文件排序
     *
     * @param dir
     * @return
     */
    public static List<String> sort(final String dir) {
        if(TextUtils.isEmpty(dir)){
            return null;
        }
        List<String> paths = new ArrayList<String>();
        File file = new File(dir);
        if(file == null){
            return paths;
        }
        if (file.exists() && file.isDirectory() && file.listFiles()!= null && file.listFiles().length > 0) {
            final File[] childs = file.listFiles();
            for (File child : childs) {
                paths.add(child.getAbsolutePath());
            }
        }
        if (paths != null && paths.size() > 1) {
            Collections.sort(paths, Collections.reverseOrder());
        }
        return paths;
    }

    /**
     * @param basePath LivePath.PATH_BASE
     * @param fileName
     * @param str
     */
    public static void writeFile(String basePath, String fileName, String str) {
        try {
            File file = new File(basePath, fileName);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(str);
            bw.flush();
            fw.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param basePath LivePath.PATH_BASE
     * @param fileName
     * @return
     */
    public static String readFile(String basePath, String fileName) {
        StringBuilder result = new StringBuilder();
        FileReader reader = null;
        BufferedReader buf = null;
        try {
            reader = new FileReader(new File(basePath, fileName));
            buf = new BufferedReader(reader);
            String line = null;
            while ((line = buf.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (buf != null) {
                    buf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();

    }

    public static String readFileByLines(String fileName) {
        StringBuilder builder = new StringBuilder();
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));//new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                line++;
                //tempString = new String(tempString.getBytes("gb2312"), "utf-8");
                while (tempString.startsWith("\t")) {
                    tempString = tempString.replace("\t", "");
                }
                while (tempString.endsWith("\t")) {
                    tempString = tempString.replace("\t", "");
                }
                while (tempString.endsWith("\r")) {
                    tempString = tempString.replace("\r", "");
                }
                builder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return builder.toString();
    }


    /**
     * 将JSON gZip压缩成文件
     *
     * @param content
     * @param path
     * @return
     * @throws IOException
     * @author: xiaozhenhua
     * @data:2013-9-11 下午3:36:49
     */
    public static boolean compress(String content, String path) throws IOException {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        FileOutputStream os = new FileOutputStream(path);
        GZIPOutputStream gz = new GZIPOutputStream(os);
        byte[] strbt = content.getBytes("utf-8");
        byte[] buffer = new byte[1024];
        int len = 0;
        int strlen = strbt.length;
        while (len < strlen) {
            if (strlen < buffer.length) {
                gz.write(strbt, 0, strlen);
            } else {
                if ((strlen - len) < buffer.length) {
                    gz.write(strbt, len, strlen - len);
                } else {
                    gz.write(strbt, len, buffer.length);
                }
            }
            len += buffer.length;
        }
        gz.close();
        os.close();
        return true;
    }

    /**
     * 将二进制数据写入文件
     *
     * @param path
     * @author: xiaozhenhua
     * @data:2012-7-28 上午10:21:06
     */
    public static void writeFile(byte[] content, String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (content != null) {
            try {
                FileOutputStream os = new FileOutputStream(file);
                os.write(content);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeObjectToFile(String fileName,Object obj)
    {
        if(TextUtils.isEmpty(fileName)){
            return;
        }
        File file =new File(fileName);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        ObjectOutputStream objOut = null;

        try {
            out = new FileOutputStream(file);
            objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            System.out.println("write object success!");
        } catch (Exception e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }finally {
            CloseableUtils.close(objOut);
            CloseableUtils.close(out);
        }
    }

    public static Object readObjectFromFile(String fileName)
    {
        if(TextUtils.isEmpty(fileName)){
            return null;
        }
        Object temp=null;
        File file =new File(fileName);
        FileInputStream in = null;
        ObjectInputStream objIn = null;
        try {
            in = new FileInputStream(file);
            objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CloseableUtils.close(in);
            CloseableUtils.close(objIn);
        }
        return temp;
    }

    /**
     * 登录数据反序列化，这里是为了处理DataLogin从主工程迁移到BaseModule后反序列化的问题，其他的序列化不要使用此方法
     * @param fileName 文件地址
     * @return
     */
    public static Object readAccountObjectFromFile(String fileName){
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        Object temp = null;
        File file = new File(fileName);
        FileInputStream in = null;
        ObjectInputStream objIn = null;
        try {
            in = new FileInputStream(file);
            objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.close(in);
            CloseableUtils.close(objIn);
        }
        return temp;
    }

    /**
     * 获取目录下所有文件
     * @param dirPath
     * @return
     */
    public static List<File> getAllFiles(String dirPath){
        List<File> allFiles = new ArrayList<>();
        File[] listFiles = new File(dirPath).listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            File file = listFiles[i];
            if(file.isFile()){
                allFiles.add(file);
            }else if(file.isDirectory()){
                allFiles.addAll(getAllFiles(file.getAbsolutePath()));
            }
        }
        return allFiles;
    }

    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    public static long getAvailInternalRomSize(){
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());//调用该类来获取磁盘信息（而getDataDirectory就是内部存储）
        long counts = statFs.getAvailableBlocks() ; //获取可用的block数
        long size = statFs.getBlockSize(); //每格所占的大小，一般是4KB==
        Log.i("db","Environment.getDataDirectory().getPath():"+Environment.getDataDirectory().getPath()+" size:"+counts * size);
        return counts * size;//可用内部存储大小
    }

    public static long getAvailExternalRomSize(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());//调用该类来获取磁盘信息（而getExternalStorageDirectory就是外置存储）
            long counts = statFs.getAvailableBlocks() ; //获取可用的block数
            long size = statFs.getBlockSize(); //每格所占的大小，一般是4KB==
            return counts * size;//可用内部存储大小
        }
        return 0;
    }

    /**
     * 磁盘大小是否够用
     */
    public static boolean checkDiskAvailable(long allocSize){
        return getAvailExternalRomSize() > allocSize || getAvailInternalRomSize() > allocSize;
    }

    /**
     * 删除当前目录下所有文件，不包括子文件夹下的文件
     * @param file
     */
    public static void delCurrentDirFiles(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if(childFiles != null && childFiles.length > 0){
                for (File f : childFiles){
                    if(f != null) {
                        f.delete();
                    }
                }
            }
        }
    }

    /**
     * 指定路径的文件是否存在
     * @param path
     * @return
     */
    public static boolean fileExists(String path){
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        return file.exists();
    }

    /**
     * Creates a new, empty file on the file system. if the parent directory
     * does not exist, create folders automatically. if the file is exists,
     * return {@code true}
     * 以指定路径创建文件
     * @param path 指定路径
     * @return true 创建成功， false 创建失败
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        File parent = file.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                return false;
            }
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过Uri获取文件路径
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {

            int sdkVersion = Build.VERSION.SDK_INT;
            if (sdkVersion >= 19) {
                // api >= 19
                return getRealPathFromUriAboveApi19(context, uri);
            } else {
                // api < 19
                return getRealPathFromUriBelowAPI19(context, uri);
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }
    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }


    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }
    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static byte[] getAssets(Context context, String path){
        byte[] data = null;
        try {
            InputStream is = context.getAssets().open(path);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
