package com.example.drivemypackage.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.Constants.Util;
import com.example.drivemypackage.MainActivity;
import com.example.drivemypackage.MainActivityDealers.DealerMainActivity;
import com.example.drivemypackage.MainActivityDrivers.DriversMainActivity;
import com.example.drivemypackage.R;
import com.example.drivemypackage.exceptionMessage.ExceptionMessageActivity;
import com.example.drivemypackage.signup.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class loginActivity extends AppCompatActivity {

    private TextInputEditText Email, Password;
    private String email, password;
    private Button loginDri,loginDre, signup;
    private View progressBar;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        Email = findViewById(R.id.login_username);
        Password = findViewById(R.id.login_password);
        loginDri = findViewById(R.id.login_driver_btn);
        loginDre = findViewById(R.id.login_dealer_btn);
        signup = findViewById(R.id.login_page_new_user);
        progressBar = findViewById(R.id.login_page_progress);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        databaseReference1 = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);
        databaseReference2 = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DEALERS);
    }

    public void logindealer(View v)
    {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(email.equals(""))
        {
            Email.setError("Enter email Address");
        }
        else if(password.equals(""))
        {
            Password.setError("Enter password");
        }
        else
        {
            if(Util.connectionAvailable(this)) {
                progressBar.setVisibility(View.VISIBLE);

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if (task.isSuccessful()) {
                            startActivity(new Intent(loginActivity.this, DealerMainActivity.class));
                            progressBar.setVisibility(View.GONE);

                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(loginActivity.this, "Login Failed " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(loginActivity.this, ExceptionMessageActivity.class));
            }
        }
    }

    public void logindriver(View v)
    {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(email.equals(""))
        {
            Email.setError("Enter email Address");
        }
        else if(password.equals(""))
        {
            Password.setError("Enter password");
        }
        else
        {
            if(Util.connectionAvailable(this)) {
                progressBar.setVisibility(View.VISIBLE);

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(loginActivity.this, DriversMainActivity.class));
                            progressBar.setVisibility(View.GONE);

                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(loginActivity.this, "Login Failed " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(loginActivity.this, ExceptionMessageActivity.class));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {


            databaseReference1.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {


                        startActivity(new Intent(loginActivity.this,DriversMainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                    else
                    {

                        startActivity(new Intent(loginActivity.this,DealerMainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            });



        }


        progressBar.setVisibility(View.GONE);

    }

}