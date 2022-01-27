package com.chn.cookies.meta;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    public static final String PHONE_SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    static private String[] permissionArr=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    static private Activity contextMain = null;
    /// permission

    /**
     * 检验是否所有需要的权限都已申请，只有当所有权限都授予之后，才能进行MapGIS的环境初始化
     */
    public static boolean hasGetAllPermission(String[] permissionArray){
        List<String> needPermission=new ArrayList<String>();
        for(String permission:permissionArr){
            if (ContextCompat.checkSelfPermission(contextMain, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermission.add(permission);
            }
        }
        //如果所有权限都已具备，则返回true，否则返回false
        if (needPermission.size()==0) {
            return true;
        }
        else return false;
    }

    /**
     * 请求权限
     * @param permission
     */
    public static void requestPermissions(String permission){
        ActivityCompat.requestPermissions(contextMain,new String[]{permission},MY_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * 请求权限回调
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CODE){
            if (grantResults.length > 0){
                //权限没有授予
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    switch (permissions[0]){
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        case Manifest.permission.READ_EXTERNAL_STORAGE:
                            showMyDialog("此程序需要存储的读写权限，请点击设置前往权限模块授予。\n未授予权限程序无法正常工作");
                            break;
                        case Manifest.permission.READ_PHONE_STATE:
                            showMyDialog("此程序需要电话权限，请点击设置前往权限模块授予。\n未授予权限程序无法正常工作");
                            break;
                        case Manifest.permission.INTERNET:
                            showMyDialog("此程序需要Internet权限，请点击设置前往权限模块授予。\n未授予权限程序无法正常工作");
                            break;
                        case Manifest.permission.LOCATION_HARDWARE:
                            //showMyDialog("此程序需要location权限，请点击设置前往权限模块授予。\n未授予权限程序无法正常工作");
                            break;
                        default:
                            break;
                    }
                }
                //权限已授予
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //如果权限授予了，则重新判断所有权限，没有授予的继续请求
                    checkPermissions(permissionArr);
                }
            }
        }
    }


    public static void Check(Activity context){
        contextMain = context;
        checkPermissions(permissionArr);
    }

    protected static void Init()
    {
        //com.chn.cookies.meta.Envi.InitQt(this);
        String strRootPath = PHONE_SDCARD_PATH + "/config/";
        com.chn.cookies.meta.Envi.Init( contextMain,strRootPath);
    }

    /**
     * 检查权限，没有授权即请求授权
     * @param permissionArray
     */
    public static void checkPermissions(String[] permissionArray){
        //检查所有的权限，如果所有权限具备，则初始化开发环境
        if (hasGetAllPermission(permissionArr)) {
            //初始化开发环境
            Init();
        }
        //不是所有权限都具备，则继续检查请求
        else {
            for (String permission:permissionArray){
                if (ContextCompat.checkSelfPermission(contextMain, permission) != PackageManager.PERMISSION_GRANTED){
                    //没有授权
                    requestPermissions(permission);
                }
            }
        }
    }

    /**
     * 弹出自定义对话框：提示权限的重要性，并引导用户前往程序的应用管理界面手动开启权限
     * @param message
     */
    private static void showMyDialog(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(contextMain);
        builder.setTitle("权限申请");
        builder.setMessage(message);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //打开系统中应用设置的界面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + contextMain.getPackageName()));
                contextMain.startActivity(intent);

                contextMain.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //如果取消，则退出应用
                contextMain.finish();
            }
        });
        builder.setCancelable(false);//点击返回键和空白区域不取消对话框
        builder.show();
    }

}
