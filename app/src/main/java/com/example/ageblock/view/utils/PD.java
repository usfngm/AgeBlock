package com.example.ageblock.view.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class PD {
    private static PD _instance;
    private ProgressDialog pd;
    private PD()
    {

    }

    public PD init(Context activity, String msg)
    {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setMessage(msg);
        return _instance;
    }

    public void show()
    {
        pd.show();
    }

    public void hide()
    {
        pd.dismiss();
    }

    public static PD get() {
        if (_instance == null)
        {
            _instance = new PD();
        }
        return _instance;
    }
}
