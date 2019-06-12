package com.example.ageblock.view.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import com.crowdfire.cfalertdialog.CFAlertDialog;

public class YesNoAD {
    private static YesNoAD _instance;
    private CFAlertDialog.Builder builder;

    private YesNoAD() {

    }

    public void init(Context activity, String msg, final BtnPress i) {
        builder = new CFAlertDialog.Builder(activity)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setCancelable(false)
                .setTitle("Alert")
                .setMessage(msg)
                .addButton("Yes", Color.WHITE, Color.parseColor("#e38418"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.yes();
                        dialog.dismiss();
                    }
                })
                .addButton("No", Color.WHITE, Color.parseColor("#e38418"), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.no();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static YesNoAD get() {
        if (_instance == null) {
            _instance = new YesNoAD();
        }
        return _instance;
    }
}
