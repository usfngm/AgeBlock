package com.example.ageblock.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.ageblock.R;
import com.example.ageblock.api.API;
import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.model.User;
import com.example.ageblock.view.utils.AD;
import com.example.ageblock.view.utils.PD;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, signupBtn;
    EditText emailET, passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerComponents();
        registerBtnListeners();
    }

    private void registerBtnListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                hideKeyboard();

                if (TextUtils.isEmpty(emailET.getText()) || TextUtils.isEmpty(passwordET.getText()))
                {
                    AD.get().init(LoginActivity.this, "Please make sure you have filled in all the fields and try again.");
                    return;
                }

                if (!isEmailValid(emailET.getText().toString()))
                {
                    AD.get().init(LoginActivity.this, "The email you have entered is invalid. Please try again.");
                    return;
                }

                PD.get().init(LoginActivity.this, "Signing In...").show();
//                Intent i = new Intent(LoginActivity.this, VolunteerDashboard.class);
//                startActivity(i);
//                finish();
                API.getInstance().login(emailET.getText().toString(), passwordET.getText().toString(), new GenericReturnCallback<User>() {
                    @Override
                    public void success(User callback) {
                        Toast.makeText(LoginActivity.this, "Welcome back " + callback.getName(), Toast.LENGTH_LONG).show();
                        PD.get().hide();
                    }

                    @Override
                    public void error(String msg) {
                        String dialog_msg = "";
                        switch (msg) {
                            case "wrong_creds":
                                dialog_msg = "Wrong email/password combination, please try again.";
                                break;
                            default:
                                dialog_msg = msg;
                        }

                        AD.get().init(LoginActivity.this, dialog_msg);
                        PD.get().hide();
                    }
                });
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void registerComponents() {
        loginBtn = (Button) findViewById(R.id.login_loginBtn);
        signupBtn = (Button) findViewById(R.id.login_signupBtn);
        emailET = (EditText) findViewById(R.id.login_emailET);
        passwordET = (EditText) findViewById(R.id.login_passwordET);

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
