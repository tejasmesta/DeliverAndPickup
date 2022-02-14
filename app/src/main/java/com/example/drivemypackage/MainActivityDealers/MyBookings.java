package com.example.drivemypackage.MainActivityDealers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
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

public class MyBookings extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private List<BookingsModelClass> bookingsModelClassList;
    private BookingsAdapter adapter;
    private DatabaseReference databaseReference;
    private String fromS, fromC, toS, toC;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.bookings_list_recycler_view);
        textView = findViewById(R.id.no_bookings);

        bookingsModelClassList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyBookings.this));

        fillBookings();

        adapter = new BookingsAdapter(MyBookings.this, bookingsModelClassList);

        recyclerView.setAdapter(adapter);

    }

    private void fillBookings() {
        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.BOOKED);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    System.out.println(snapshot.getKey().toString());
                    bookingsModelClassList.clear();
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        System.out.println(ds.child(NodeNames.BOOKEDWHOM).getValue().toString());
                        bookingsModelClassList.add(new BookingsModelClass(ds.child(NodeNames.FROM).getValue().toString(),ds.child(NodeNames.TO).getValue().toString(),ds.child(NodeNames.BOOKEDWHOM).getValue().toString()));
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(MyBookings.this, "No Bookings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyBookings.this, "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }
}