package com.example.ageblock.view.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.ageblock.view.LoginActivity;

public class AD {
    private static AD _instance;
    private CFAlertDialog.Builder builder;

    private AD() {

    }

    public void init(Context activity, String msg) {
        builder = new CFAlertDialog.Builder(activity)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setCancelable(false)
                .setTitle("Alert")
                .setMessage(msg)
                .addButton("OK", Color.WHITE, Color.parseColor("#e38418"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static AD get() {
        if (_instance == null) {
            _instance = new AD();
        }
        return _instance;
    }
}
