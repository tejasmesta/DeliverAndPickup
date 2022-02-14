package com.example.drivemypackage.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.MainActivity;
import com.example.drivemypackage.MainActivityDealers.DealerMainActivity;
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

public class SignUpAsDealer extends AppCompatActivity {

    private TextInputEditText NAME, EMAIL, NUMBER, NATURE, WEIGHT, QUANTITY, CITY, STATE, PASSWORD, CONFIRM ;
    private String name, email, number, nature, weight, quantity, city, password, confirm,state ;
    private Button signUp;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceStates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_dealer);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        NAME = findViewById(R.id.signup_as_dealer_page_name);
        EMAIL = findViewById(R.id.signup_as_dealer_page_email);
        NUMBER = findViewById(R.id.signup_as_dealer_page_mobile);
        NATURE = findViewById(R.id.signup_as_dealer_page_nature);
        WEIGHT = findViewById(R.id.signup_as_dealer_page_weight);
        QUANTITY = findViewById(R.id.signup_as_dealer_page_quantity);
        CITY = findViewById(R.id.signup_as_dealer_city_);
        STATE = findViewById(R.id.to_state_spinner);



        PASSWORD = findViewById(R.id.signup_as_dealer_page_password);
        CONFIRM = findViewById(R.id.signup_as_dealer_page_confirm_password);
        signUp = findViewById(R.id.signup_as_a_dealer_page_signupbtn);




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

                    databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DEALERS);

                    HashMap<String,String> map = new HashMap<>();

                    map.put(NodeNames.NAME,NAME.getText().toString());
                    map.put(NodeNames.EMAIL,EMAIL.getText().toString().trim());
                    map.put(NodeNames.NUMBER,NUMBER.getText().toString().trim());
                    map.put(NodeNames.NATURE,NATURE.getText().toString());
                    map.put(NodeNames.WEIGHT,WEIGHT.getText().toString().trim());
                    map.put(NodeNames.QUANTITY,QUANTITY.getText().toString().trim());
                    map.put(NodeNames.CITY,CITY.getText().toString());
                    map.put(NodeNames.STATE,STATE.getText().toString());



                    databaseReference.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {

                            if(task1.isSuccessful())
                            {
                                Toast.makeText(SignUpAsDealer.this, "User created successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignUpAsDealer.this, DealerMainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(SignUpAsDealer.this, "Failed to create user "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignUpAsDealer.this, "Failed to update profile "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signupClick(View v)
    {
        name = NAME.getText().toString();
        email = EMAIL.getText().toString().trim();
        number = NUMBER.getText().toString().trim();
        nature = NATURE.getText().toString();
        weight = WEIGHT.getText().toString().trim();
        quantity = QUANTITY.getText().toString().trim();
        city = CITY.getText().toString();
        state = STATE.getText().toString();

        password = PASSWORD.getText().toString().trim();
        confirm = CONFIRM.getText().toString().trim();

        if(email.equals(""))
        {
            EMAIL.setError("Enter email address");
        }
        else if(name.isEmpty())
        {
            NAME.setError("Enter name");
        }
        else if(password.isEmpty())
        {
            PASSWORD.setError("Enter password");
        }
        else if(confirm.isEmpty())
        {
            CONFIRM.setError("Confirm password");
        }
        else if(city.isEmpty())
        {
            CITY.setError("Enter city");
        }

        else if(nature.isEmpty())
        {
            NATURE.setError("Enter nature");
        }
        else if(weight.isEmpty())
        {
            WEIGHT.setError("Enter weight in kgs");
        }
        else if(quantity.isEmpty())
        {
            QUANTITY.setError("Enter quantity");
        }
        else if(state.equals(""))
        {
            Toast.makeText(this, "Select a state", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty())
        {
            NUMBER.setError("Enter number");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            EMAIL.setError("Enter correct email");
        }
        else if(!password.equals(confirm))
        {
            CONFIRM.setError("Password mismatched");
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
                        Toast.makeText(SignUpAsDealer.this, "SignUp failed "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}