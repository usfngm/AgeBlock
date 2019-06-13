package com.example.ageblock.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;

public class AddElderActivity extends AppCompatActivity {

    private Button signupBtn;
    private EditText nameET, phoneET, nationalIDET, emailET, passwordET, confirmPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_elder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerComponents();
        registerBtnListeners();
    }

    private void registerBtnListeners() {
        signupBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (checkEmptyFieldsErr()) return;
                if (checkEmailValidErr()) return;
                if (checkConfirmPasswordErr()) return;

                User u = new User(nameET.getText().toString(), phoneET.getText().toString(), nationalIDET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());
                u.setType("elder");
                u.setElder_parentID(User.getLoggedUser(AddElderActivity.this).getUid());

                Log.d("JSON SENT", new Gson().toJson(u));


                PD.get().init(AddElderActivity.this, "Please Wait...").show();

                API.getInstance().registerElder(u, new GenericReturnCallback<User>() {
                    @Override
                    public void success(User callback) {
                        Toast.makeText(AddElderActivity.this, "Elder Added", Toast.LENGTH_LONG).show();
                        PD.get().hide();
                        User updatedUser = User.getLoggedUser(AddElderActivity.this);
                        updatedUser.getParent_elders().add(callback.getUid());
                        User.saveUser(AddElderActivity.this, new Gson().toJson(updatedUser));
                        finish();
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

                        AD.get().init(AddElderActivity.this, dialog_msg);
                        PD.get().hide();
                    }
                });

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean checkEmailValidErr() {
        if (!isEmailValid(emailET.getText().toString())) {
            AD.get().init(AddElderActivity.this, "The email you have entered is invalid. Please try again.");
            return true;
        }
        return false;
    }

    private boolean checkConfirmPasswordErr() {
        if (!passwordET.getText().toString().equals(confirmPasswordET.getText().toString())) {
            AD.get().init(AddElderActivity.this, "The Passwords you have entered does not match. Please try again.");
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
            AD.get().init(AddElderActivity.this, "Please make sure you have filled in all the fields and try again.");
            return true;
        }
        return false;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void registerComponents() {
        nameET = (EditText) findViewById(R.id.signup_nameET);
        phoneET = (EditText) findViewById(R.id.signup_phoneET);
        nationalIDET = (EditText) findViewById(R.id.signup_nationalIDET);
        emailET = (EditText) findViewById(R.id.signup_emailET);
        passwordET = (EditText) findViewById(R.id.signup_passwordET);
        confirmPasswordET = (EditText) findViewById(R.id.signup_confirmPasswordET);
        signupBtn = (Button) findViewById(R.id.signup_signupBtn);
    }
}
