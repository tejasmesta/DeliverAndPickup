package com.example.drivemypackage.MainActivityDrivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.MainActivityDealers.DealersAdapter;
import com.example.drivemypackage.MainActivityDealers.DriversListActivity;
import com.example.drivemypackage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriversMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<BookedByModelClass> bookedByModelClassList;
    private BookedByAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_main);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("My Bookings");
        }

        recyclerView = findViewById(R.id.driver_booked_by_list);
        bookedByModelClassList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(DriversMainActivity.this));

        fillbookedBy();

        adapter = new BookedByAdapter(DriversMainActivity.this, bookedByModelClassList);

        recyclerView.setAdapter(adapter);
    }

    private void fillbookedBy() {
        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.BOOKING);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String bookedBy = snapshot.child(NodeNames.BOOKEDBY).getValue().toString();
                    String from = snapshot.child(NodeNames.FROM).getValue().toString();
                    String to = snapshot.child(NodeNames.TO).getValue().toString();

                    bookedByModelClassList.add(new BookedByModelClass(bookedBy,from,to));

                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(DriversMainActivity.this, "No Bookings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DriversMainActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }
}