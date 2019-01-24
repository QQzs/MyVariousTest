package com.zs.various.Test;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by zs
 * Date：2019年 01月 23日
 * Time：17:03
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class PiuginResources extends Resources {
    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    public PiuginResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }


    public static AssetManager getPluginAssetManager(File apk) throws ClassNotFoundException {

        Class<?> forName = Class.forName("android.content.res.AssetManager");
        Method[] declaredMethods = forName.getDeclaredMethods();
        for (Method method : declaredMethods){
            if (method.getName().equals("addAssetPath")){
                try {
                    AssetManager manager = AssetManager.class.newInstance();
                    method.invoke(manager , apk.getAbsolutePath());
                    return manager;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
