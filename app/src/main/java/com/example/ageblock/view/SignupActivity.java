package com.example.ageblock.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ageblock.R;

public class SignupActivity extends AppCompatActivity {

    private boolean isVolunteerSelected = true;
    private Button volunteerSelectBtn, parentSelectBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        registerComponents();
        handleSignupSelection();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void handleSignupSelection() {
        volunteerSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVolunteerSelected)
                {
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
                if (isVolunteerSelected)
                {
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
    }
}
