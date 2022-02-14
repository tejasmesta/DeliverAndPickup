package com.example.drivemypackage.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.drivemypackage.R;

public class SignUpActivity extends AppCompatActivity {

    private Button dealerBtn;
    private Button driverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        dealerBtn = findViewById(R.id.signup_as_a_dealer);
        driverBtn = findViewById(R.id.signup_as_a_driver);

        dealerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignUpAsDealer.class);
                startActivity(intent);
            }
        });

        driverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignUpAsDriver.class);
                startActivity(intent);
            }
        });
    }
}