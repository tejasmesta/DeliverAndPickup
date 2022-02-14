package com.example.drivemypackage.MainActivityDealers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.drivemypackage.R;
import com.example.drivemypackage.signup.SignUpAsDealer;
import com.google.android.material.textfield.TextInputEditText;


public class DealerMainActivity extends AppCompatActivity {
    private TextInputEditText fromS, fromC, toS, toC;
    private Button search, currentbookings;
    private View progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fromS = findViewById(R.id.dealer_from_state);
        fromC = findViewById(R.id.dealer_from_city);
        toS = findViewById(R.id.dealer_to_state);
        toC = findViewById(R.id.dealer_to_city);
        progressBar = findViewById(R.id.dealers_page_progress);
        search = findViewById(R.id.dealer_search);


        currentbookings = findViewById(R.id.my_bookings);

        currentbookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DealerMainActivity.this,MyBookings.class);
                startActivity(intent);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromS.getText().toString().trim().equals(""))
                {
                    fromS.setError("Enter required details");
                }
                else if(fromC.getText().toString().trim().equals(""))
                {
                    fromC.setError("Enter required details");
                }
                else if(toS.getText().toString().trim().equals(""))
                {
                    toS.setError("Enter required details");
                }
                else if(toC.getText().toString().trim().equals(""))
                {
                    toC.setError("Enter required details");
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(DealerMainActivity.this,DriversListActivity.class);
                    intent.putExtra("fromS",fromS.getText().toString().trim().toLowerCase());
                    intent.putExtra("fromC",fromC.getText().toString().trim().toLowerCase());
                    intent.putExtra("toS",toS.getText().toString().trim().toLowerCase());
                    intent.putExtra("toC",toC.getText().toString().trim().toLowerCase());
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}

