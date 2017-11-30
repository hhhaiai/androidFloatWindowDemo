package com.example.floas.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * @Copyright © 2017 sanbo Inc. All rights reserved.
 * @Description: 初步版本的权限赋予
 * @Version: 1.0
 * @Create: 2017年8月30日 下午3:03:14
 * @author: safei
 */
public class PermissionUtils {

    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_MULTI_PERMISSION = 100;
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] requestPermissions = {PERMISSION_RECORD_AUDIO, PERMISSION_GET_ACCOUNTS,
        PERMISSION_READ_PHONE_STATE, PERMISSION_CALL_PHONE, PERMISSION_CAMERA, PERMISSION_ACCESS_FINE_LOCATION,
        PERMISSION_ACCESS_COARSE_LOCATION, PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE};

    private static final String[] logs = {"没有此权限，无法开启这个功能，请开启权限。PERMISSION_RECORD_AUDIO",
        "没有此权限，无法开启这个功能，请开启权限。PERMISSION_GET_ACCOUNTS", "没有此权限，无法开启这个功能，请开启权限。PERMISSION_CALL_PHONE",
        "没有此权限，无法开启这个功能，请开启权限。PERMISSION_READ_PHONE_STATE", "没有此权限，无法开启这个功能，请开启权限。PERMISSION_CALL_PHONE",
        "没有此权限，无法开启这个功能，请开启权限。PERMISSION_CAMERA", "没有此权限，无法开启这个功能，请开启权限。PERMISSION_ACCESS_FINE_LOCATION",
        "没有此权限，无法开启这个功能，请开启权限。PERMISSION_READ_EXTERNAL_STORAGE",
        "没有此权限，无法开启这个功能，请开启权限。PERMISSION_WRITE_EXTERNAL_STORAGE"};

    /**
     * Requests permission.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA
     *                    permission,parameters is PermissionUtils.CODE_CAMERA
     */

    public static void requestPermission(final Activity activity, final int requestCode,
                                         PermissionGrant permissionGrant) {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (activity == null) {
            return;
        }

        L.i("requestPermission requestCode:" + requestCode);
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            L.w("requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestPermissions[requestCode];

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                shouldShowRationale(activity, requestCode, requestPermission);
            } else {
                ActivityCompat.requestPermissions(activity, new String[] {requestPermission}, requestCode);
            }
        } else {
            if (permissionGrant != null) {
                permissionGrant.onPermissionGranted(requestCode);
            }
        }
    }

    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults,
                                           PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < 23) {
            return;
        }

        Map<String, Integer> perms = new HashMap<String, Integer>();
        ArrayList<String> notGranted = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            if (permissionGrant != null) {
                permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
            }
        } else {
            openSettingActivity(activity, "those permission need granted!");
        }
    }

    /**
     * 一次申请多个权限
     */

    public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {
        final List<String> permissionsList = getNoGrantedPermission(activity, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                CODE_MULTI_PERMISSION);
        } else if (shouldRationalePermissionsList.size() > 0) {

            showMessageOKCancel(activity, "should open those permission", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity,
                        shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                        CODE_MULTI_PERMISSION);
                }
            });
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode,
                                            final String requestPermission) {
        showMessageOKCancel(activity, "Rationale: " + logs[requestCode], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, new String[] {requestPermission}, requestCode);
                L.d("showMessageOKCancel requestPermissions:" + requestPermission);
            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message,
                                            DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null).create().show();
    }

    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode, String[] permissions,
                                                int[] grantResults, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, permissionGrant);
            return;
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            openSettingActivity(activity, "Result" + logs[requestCode]);
        }
    }

    /**
     * 打开授权页面
     *
     * @param activity
     * @param message
     */
    private static void openSettingActivity(final Activity activity, String message) {

        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 其实就是将没有授权的记录下来
     *
     * @param activity
     * @param isShouldRationale true: return no granted and
     *                          shouldShowRequestPermissionRationale permissions, false:return
     *                          no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    public static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<String>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];

            if (!checkPermission(activity, requestPermission)) {
                // 用户是否禁止,true表明用户彻底禁止弹出权限提示
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                } else {
                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                }
            }

        }
        return permissions;
    }

    /**
     * 权限申请.所有版本通用的.
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer)method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }

    /**
     * @Copyright © 2017 sanbo Inc. All rights reserved.
     * @Description: 简单版本的权限访问
     * @Version: 1.0
     * @Create: 2017年8月30日 下午3:04:55
     * @author: safei
     */
    public class SimpleQequest {
        private final int REQUEST_EXTERNAL_STORAGE = 1;
        private String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

        public void verifyStoragePermissions(Activity activity) {
            if (!PermissionUtils.checkPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        }
    }

}