package com.chn.cookies.meta;

import android.content.Context;
import android.os.Environment;

import org.qtproject.qt5.android.QtNative;

import java.util.ArrayList;

import dalvik.system.DexClassLoader;

public class Envi {

    public static final String PHONE_SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String FILE_PATH = PHONE_SDCARD_PATH + "/data/WuHan.png";

    private int binding ;
    public static void Init(Context context, String strRoot) {
        InitQt(context);
    }

    public static int InitQt(Context context) {

        String nativeLibraryPrefix = context.getApplicationInfo().nativeLibraryDir + "/";

        DexClassLoader classLoader = new DexClassLoader("", // .jar/.apk files
                context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), // directory where optimized DEX files should be written.
                null, // libs folder (if exists)
                context.getClassLoader());

        QtNative.setClassLoader(classLoader);

        String[] qtLibs = {"c++_shared","Qt5Core","Qt5Gui","Qt5Widgets","qtforandroid"};
        ArrayList<String> libraryList = new ArrayList<String>();

        String libPrefix = nativeLibraryPrefix + "lib";
        for (int i = 0; i < qtLibs.length; i++)
            libraryList.add(libPrefix + qtLibs[i] + ".so");
        QtNative.loadQtLibraries(libraryList);

        return 1;
    }

    public void Destroy() {
        binding = 0;
    }
    /*
     *
     * @param offlineMapPath
     */
    static void showMap(String offlineMapPath) {


    }
}
