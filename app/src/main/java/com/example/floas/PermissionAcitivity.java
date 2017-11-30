package com.example.floas;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import com.example.floas.utils.T;

/**
 * 权限申请.
 */
public class PermissionAcitivity extends Activity {

    public static int PERMISSION_REQ = 0x123456;

    private String[] mPermission = new String[] {
        Manifest.permission.INTERNET,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private List<String> mRequestPermission = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String one : mPermission) {
                if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                    mRequestPermission.add(one);
                }
            }
            if (!mRequestPermission.isEmpty()) {
                this.requestPermissions(mRequestPermission.toArray(new String[mRequestPermission.size()]),
                    PERMISSION_REQ);
                return;
            }
        }
        startActiviy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 版本兼容
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
            startActiviy();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQ) {
            if (resultCode == 0) {
                this.finish();
            }
        }
    }

    public void startActiviy() {
        if (mRequestPermission.isEmpty()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("register...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            new AlertDialog.Builder(this)
                .setTitle("请选择相机")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(new String[] {"后置相机", "前置相机"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressDialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PermissionAcitivity.this.finish();
                            }
                        }, 3000);
                        startDetector(which);
                    }
                })
                .show();
        } else {
            T.show("PERMISSION DENIED!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PermissionAcitivity.this.finish();
                }
            }, 3000);
        }
    }

    private void startDetector(int which) {
        Intent it = new Intent(PermissionAcitivity.this, MainActivity.class);
        it.putExtra("Camera", which);
        startActivity(it);
    }
}
