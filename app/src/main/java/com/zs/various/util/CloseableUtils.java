package com.zs.various.util;

import android.database.Cursor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 资源关闭工具类
 *
 * @author wangheng
 */
public class CloseableUtils {

    public static void close(InputStream t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Socket t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Cursor t){
        if(t != null){
            t.close();
        }
    }
    public static void close(RandomAccessFile t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(ServerSocket t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Reader t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Writer t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(OutputStream t){
        if(t != null){
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
