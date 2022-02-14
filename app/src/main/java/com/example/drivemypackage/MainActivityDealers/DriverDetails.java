package com.example.drivemypackage.MainActivityDealers;

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
import com.example.drivemypackage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverDetails extends AppCompatActivity {

    private TextView trucknumber, mobile, name, exp, emailid, transname, cap;
    private Button call, mail;
    private View progressBar;
    private String userID;
    private DatabaseReference databaseReference;
    String number;
    String mailidtosend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        name = findViewById(R.id.single_name);
        exp = findViewById(R.id.single_exp);
        trucknumber = findViewById(R.id.single_plate);
        mobile = findViewById(R.id.single_number);
        emailid = findViewById(R.id.single_emailid);
        transname = findViewById(R.id.single_transname);
        cap = findViewById(R.id.single_cap);
        call = findViewById(R.id.call_driver);
        mail = findViewById(R.id.mail_driver);
        progressBar = findViewById(R.id.single_progress);

        Intent intent = getIntent();
        userID = intent.getExtras().getString("userId");

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String a = "Name: ";
                    String n = snapshot.child(NodeNames.NAME).getValue().toString();
                    a = a+n;
                    name.setText(a);

                    String e = "Experience: ";
                    String ev = snapshot.child(NodeNames.EXP).getValue().toString();
                    e = e+ev;
                    exp.setText(e+" years");

                    String m = "Mobile Number: ";
                    number = snapshot.child(NodeNames.NUMBER).getValue().toString();
                    m = m+number;
                    mobile.setText(m);

                    String ed = "Email id: ";
                    String edv = snapshot.child(NodeNames.EMAIL).getValue().toString();
                    mailidtosend = edv;
                    ed = ed+edv;
                    emailid.setText(ed);

                    String t = "Transporter Name: ";
                    String tv = snapshot.child(NodeNames.TRANSNAME).getValue().toString();
                    t = t+tv;
                    transname.setText(t);

                    String c = "Capacity: ";
                    String cv = snapshot.child(NodeNames.CAPACITY).getValue().toString();
                    c = c+cv;
                    cap.setText(c+" kgs");

                    String tp = snapshot.child(NodeNames.TRUCK).getValue().toString();
                    trucknumber.setText(tp);
                }
                else
                {

                    Toast.makeText(DriverDetails.this, "Error fetching details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(DriverDetails.this, "Error fetching details", Toast.LENGTH_SHORT).show();
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

            if (ActivityCompat.checkSelfPermission(DriverDetails.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(DriverDetails.this, PERMISSIONS_STORAGE, 9);
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
            Toast.makeText(DriverDetails.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(DriverDetails.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            DriverDetails.this.startActivity(callIntent);
        }else{
            Toast.makeText(DriverDetails.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}