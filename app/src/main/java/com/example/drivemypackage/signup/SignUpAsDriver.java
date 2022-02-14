package com.example.drivemypackage.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.MainActivity;
import com.example.drivemypackage.MainActivityDrivers.DriversMainActivity;
import com.example.drivemypackage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpAsDriver extends AppCompatActivity {

    private TextInputEditText NAME,AGE, EMAIL, NUMBER, TRUCK, CAPACITY,TRANS, EXP, STATE1, CITY1, STATE2, CITY2, STATE3, CITY3, PASSWORD, CONFIRM, STATE11, CITY11, STATE22, CITY22, STATE33, CITY33;
    private String name, email, number, truck, capacity, exp, trans, state1, city1, state2, city2, state3, city3, password, confirm, state11, city11, state22, city22, state33, city33;
    private Button signUp;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_driver);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        NAME = findViewById(R.id.signup_as_driver_page_name);
        AGE = findViewById(R.id.signup_as_driver_page_age);
        TRANS = findViewById(R.id.signup_as_driver_page_trans_name);
        EMAIL = findViewById(R.id.signup_as_driver_page_email);
        NUMBER = findViewById(R.id.signup_as_driver_page_mobile);
        TRUCK = findViewById(R.id.signup_as_driver_page_truck_number);
        CAPACITY = findViewById(R.id.signup_as_driver_page_capacity);
        EXP = findViewById(R.id.signup_as_driver_page_experience);
        STATE1 = findViewById(R.id.signup_as_driver_state_1);
        CITY1 = findViewById(R.id.signup_as_driver_page_city_1);
        STATE2 = findViewById(R.id.signup_as_driver_state_2);
        CITY2 = findViewById(R.id.signup_as_driver_page_city_2);
        STATE3 = findViewById(R.id.signup_as_driver_state_3);
        CITY3 = findViewById(R.id.signup_as_driver_page_city_3);
        STATE11 = findViewById(R.id.signup_as_driver_state_1_to);
        CITY11 = findViewById(R.id.signup_as_driver_page_city_1_to);
        STATE22 = findViewById(R.id.signup_as_driver_state_2_to);
        CITY22 = findViewById(R.id.signup_as_driver_page_city_2_to);
        STATE33 = findViewById(R.id.signup_as_driver_state_3_to);
        CITY33 = findViewById(R.id.signup_as_driver_page_city_3_to);
        PASSWORD = findViewById(R.id.signup_as_driver_page_password);
        CONFIRM = findViewById(R.id.signup_as_driver_page_confirm_password);
        signUp = findViewById(R.id.signup_as_a_driver_page_signupbtn);

    }

    public void updateProfile()
    {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(NAME.getText().toString())
                .build();

        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    String userId = firebaseUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);

                    HashMap<String,String> map = new HashMap<>();

                    map.put(NodeNames.NAME,NAME.getText().toString());
                    map.put(NodeNames.AGE,AGE.getText().toString().trim());
                    map.put(NodeNames.EMAIL,EMAIL.getText().toString().trim());
                    map.put(NodeNames.NUMBER,NUMBER.getText().toString().trim());
                    map.put(NodeNames.TRUCK,TRUCK.getText().toString().trim());
                    map.put(NodeNames.CAPACITY,CAPACITY.getText().toString().trim());
                    map.put(NodeNames.TRANSNAME,TRANS.getText().toString());
                    map.put(NodeNames.EXP,EXP.getText().toString().trim());
                    map.put(NodeNames.STATE1,STATE1.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY1,CITY1.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATE2,STATE2.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY2,CITY2.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATE3,STATE3.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY3,CITY3.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATE11,STATE11.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY11,CITY11.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATE22,STATE22.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY22,CITY22.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATE33,STATE33.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.CITY33,CITY33.getText().toString().trim().toLowerCase());
                    map.put(NodeNames.STATUS,"unbooked");


                    databaseReference.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {

                            if(task1.isSuccessful())
                            {
                                Toast.makeText(SignUpAsDriver.this, "User created successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignUpAsDriver.this, DriversMainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(SignUpAsDriver.this, "Failed to create user "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignUpAsDriver.this, "Failed to update profile "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUP(View v)
    {
        name = NAME.getText().toString();
        email = EMAIL.getText().toString().trim();
        number = NUMBER.getText().toString().trim();
        truck = TRUCK.getText().toString().trim();
        capacity = CAPACITY.getText().toString().trim();
        exp = EXP.getText().toString().trim();
        state1 = STATE1.getText().toString().trim();
        city1 = STATE1.getText().toString().trim();
        state2 = STATE2.getText().toString().trim();
        city2 = CITY2.getText().toString().trim();
        state3 = STATE3.getText().toString().trim();
        city3 = CITY3.getText().toString().trim();
        password = PASSWORD.getText().toString().trim();
        confirm = CONFIRM.getText().toString().trim();

        if(name.equals(""))
        {
            NAME.setError("Enter name");
        }
        else if(email.equals(""))
        {
            EMAIL.setError("Enter email");
        }
        else if(number.equals(""))
        {
            NUMBER.setError("Enter number");
        }
        else if(truck.equals(""))
        {
            TRUCK.setError("Enter truck number");
        }
        else if(capacity.equals(""))
        {
            CAPACITY.setError("Enter capacity");
        }
        else if(exp.equals(""))
        {
            EXP.setError("Enter experience");
        }
        else if(state1.equals(""))
        {
            STATE1.setError("Enter state");
        }
        else if(city1.equals(""))
        {
            CITY1.setError("Enter city");
        }
        else if(state2.equals(""))
        {
            STATE2.setError("Enter state");
        }
        else if(city2.equals(""))
        {
            CITY2.setError("Enter city");
        }
        else if(state3.equals(""))
        {
            STATE3.setError("Enter state");
        }
        else if(city3.equals(""))
        {
            CITY3.setError("Enter city");
        }
        else if(password.equals(""))
        {
            PASSWORD.setError("Enter password");
        }
        else if(CONFIRM.equals(""))
        {
            CONFIRM.setError("Confirm password");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            EMAIL.setError("Enter correct email");
        }
        else
        {
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        firebaseUser = firebaseAuth.getCurrentUser();

                        updateProfile();

                    }
                    else
                    {
                        Toast.makeText(SignUpAsDriver.this, "SignUp failed "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}