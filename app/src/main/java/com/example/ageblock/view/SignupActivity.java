package com.example.ageblock.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ageblock.R;
import com.example.ageblock.api.API;
import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.model.User;
import com.example.ageblock.view.utils.AD;
import com.example.ageblock.view.utils.PD;
import com.google.gson.Gson;

public class SignupActivity extends AppCompatActivity {

    private boolean isVolunteerSelected = true;
    private Button volunteerSelectBtn, parentSelectBtn, signupBtn;
    private EditText nameET, phoneET, nationalIDET, emailET, passwordET, confirmPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerComponents();
        handleSignupSelection();
        registerBtnListeners();
    }

    private void saveUser(User callback) {
        SharedPreferences sp = getSharedPreferences("app", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", new Gson().toJson(callback));
        editor.commit();
    }

    private void login(User u) {
        // Acc Type Check then go to appropriate dashboard
        if (u.getType().equals("parent"))
        {
            Intent i = new Intent(SignupActivity.this, ParentDashboard.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(SignupActivity.this, VolunteerDashboard.class);
            startActivity(i);
            finish();
        }
    }

    private void registerBtnListeners() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmptyFieldsErr()) return;
                if (checkEmailValidErr()) return;
                if (checkConfirmPasswordErr()) return;

                User u = new User(nameET.getText().toString(), phoneET.getText().toString(), nationalIDET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());
                if (isVolunteerSelected) u.setType("volunteer");
                else u.setType("parent");

                PD.get().init(SignupActivity.this, "Please Wait...").show();

                API.getInstance().register(u, new GenericReturnCallback<User>() {
                    @Override
                    public void success(User callback) {
                        Toast.makeText(SignupActivity.this, "Your account has been created", Toast.LENGTH_LONG).show();
                        PD.get().hide();
                        saveUser(callback);
                        login(callback);
                    }

                    @Override
                    public void error(String msg) {
                        String dialog_msg = "";
                        switch (msg) {
                            case "auth/email-already-exists":
                                dialog_msg = "The email used already exists. Please use another one and try again.";
                                break;
                            case "no_server":
                                dialog_msg = "Cannot connect to the server. Please try again.";
                                break;
                            default:
                                dialog_msg = msg;
                        }

                        AD.get().init(SignupActivity.this, dialog_msg);
                        PD.get().hide();
                    }
                });

            }
        });
    }

    private boolean checkEmailValidErr() {
        if (!isEmailValid(emailET.getText().toString())) {
            AD.get().init(SignupActivity.this, "The email you have entered is invalid. Please try again.");
            return true;
        }
        return false;
    }

    private boolean checkConfirmPasswordErr() {
        if (!passwordET.getText().toString().equals(confirmPasswordET.getText().toString())) {
            AD.get().init(SignupActivity.this, "The Passwords you have entered does not match. Please try again.");
            return true;
        }
        return false;
    }

    private boolean checkEmptyFieldsErr() {
        if (TextUtils.isEmpty(nameET.getText()) ||
                TextUtils.isEmpty(phoneET.getText()) ||
                TextUtils.isEmpty(nationalIDET.getText()) ||
                TextUtils.isEmpty(emailET.getText()) ||
                TextUtils.isEmpty(passwordET.getText()) ||
                TextUtils.isEmpty(confirmPasswordET.getText())) {
            AD.get().init(SignupActivity.this, "Please make sure you have filled in all the fields and try again.");
            return true;
        }
        return false;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void handleSignupSelection() {
        volunteerSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVolunteerSelected) {
                    volunteerSelectBtn.setBackgroundResource(R.drawable.signup_selected);
                    parentSelectBtn.setBackgroundResource(R.drawable.signup_not_selected);
                    isVolunteerSelected = true;
                    volunteerSelectBtn.setTextColor(Color.WHITE);
                    parentSelectBtn.setTextColor(Color.BLACK);
                }
            }
        });

        parentSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolunteerSelected) {
                    volunteerSelectBtn.setBackgroundResource(R.drawable.signup_not_selected);
                    parentSelectBtn.setBackgroundResource(R.drawable.signup_selected);
                    isVolunteerSelected = false;
                    volunteerSelectBtn.setTextColor(Color.BLACK);
                    parentSelectBtn.setTextColor(Color.WHITE);
                }
            }
        });
    }

    private void registerComponents() {
        volunteerSelectBtn = (Button) findViewById(R.id.signup_volunteerBtn);
        parentSelectBtn = (Button) findViewById(R.id.signup_parentBtn);
        nameET = (EditText) findViewById(R.id.signup_nameET);
        phoneET = (EditText) findViewById(R.id.signup_phoneET);
        nationalIDET = (EditText) findViewById(R.id.signup_nationalIDET);
        emailET = (EditText) findViewById(R.id.signup_emailET);
        passwordET = (EditText) findViewById(R.id.signup_passwordET);
        confirmPasswordET = (EditText) findViewById(R.id.signup_confirmPasswordET);
        signupBtn = (Button) findViewById(R.id.signup_signupBtn);
    }
}
