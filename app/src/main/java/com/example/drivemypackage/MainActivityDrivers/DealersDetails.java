package com.example.drivemypackage.MainActivityDrivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.MainActivityDealers.DriverDetails;
import com.example.drivemypackage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DealersDetails extends AppCompatActivity {

    private TextView name, mobile, email, nature, weight, quantity;
    private Button call, mail;
    private DatabaseReference databaseReference;
    private String userID;
    private String number, mailidtosend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealers_details);

        name = findViewById(R.id.single_name);
        mobile = findViewById(R.id.single_number);
        email = findViewById(R.id.single_emailid);
        nature = findViewById(R.id.single_nature);
        weight = findViewById(R.id.single_weight);
        quantity = findViewById(R.id.single_qty);
        call = findViewById(R.id.call_dealer);
        mail = findViewById(R.id.mail_dealer);

        Intent intent = getIntent();
        userID = intent.getExtras().getString("userId");

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DEALERS);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String n = "Name: "+snapshot.child(NodeNames.NAME).getValue().toString();
                    name.setText(n);

                    number = "Mobile Number: "+snapshot.child(NodeNames.NUMBER).getValue().toString();
                    mobile.setText(number);

                    String e = snapshot.child(NodeNames.EMAIL).getValue().toString();
                    mailidtosend = e;
                    email.setText("Email id: "+e);

                    String nn = "Nature: "+snapshot.child(NodeNames.NATURE).getValue().toString();
                    nature.setText(nn);

                    String w = "Weight: "+snapshot.child(NodeNames.WEIGHT).getValue().toString()+" kgs";
                    nature.setText(w);

                    String q = "Quantity: "+snapshot.child(NodeNames.QUANTITY).getValue().toString();
                    nature.setText(q);

                }
                else
                {

                    Toast.makeText(DealersDetails.this, "Error fetching details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(DealersDetails.this, "Error fetching details", Toast.LENGTH_SHORT).show();
            }

        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(number);
                onCallBtnClick();
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mailidtosend));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Regarding package delivery");
                startActivity(Intent.createChooser(emailIntent, "Send Mail"));
            }
        });


    }

    public void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(DealersDetails.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(DealersDetails.this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            Toast.makeText(DealersDetails.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(DealersDetails.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            DealersDetails.this.startActivity(callIntent);
        }else{
            Toast.makeText(DealersDetails.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}