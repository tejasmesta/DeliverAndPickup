package com.example.drivemypackage.MainActivityDealers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriversListActivity extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private List<DriversModelClass> driversModelClasses;
    private DealersAdapter adapter;
    private DatabaseReference databaseReference;
    private String fromS, fromC, toS, toC;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_list);

        Intent intent = getIntent();

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        fromS = intent.getExtras().getString("fromS");
        fromC = intent.getExtras().getString("fromC");
        toS = intent.getExtras().getString("toS");
        toC = intent.getExtras().getString("toC");

        textView = findViewById(R.id.no_drivers);
        recyclerView = findViewById(R.id.drivers_list_recycler_view);
        progressBar = findViewById(R.id.drivers_list_progress);

        driversModelClasses = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(DriversListActivity.this));

        fillDrivers();


             adapter = new DealersAdapter(DriversListActivity.this, driversModelClasses);

            recyclerView.setAdapter(adapter);


    }

    public void fillDrivers()
    {
        progressBar.setVisibility(View.VISIBLE);

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                driversModelClasses.clear();

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    final String userId = ds.getKey();

                    final String status = ds.child(NodeNames.STATUS).getValue().toString();

                    if(status.equals("booked"))
                    {
                        continue;
                    }
                    else
                    {
                        if(ds.child(NodeNames.STATE1).getValue().toString().equals(fromS) && ds.child(NodeNames.CITY1).getValue().toString().equals(fromC))
                        {
                            System.out.println(fromC);

                            String a = ds.child(NodeNames.STATE11).getValue().toString();

                            String b = ds.child(NodeNames.CITY11).getValue().toString();


                            if(a.equals(toS) && b.equals(toC)) {
                                System.out.println(fromC);

                                driversModelClasses.add(new DriversModelClass(userId, ds.child(NodeNames.NAME).getValue().toString(), ds.child(NodeNames.NUMBER).getValue().toString(),
                                        ds.child(NodeNames.STATUS).getValue().toString(),fromS, fromC, toS, toC));
                            }


                        }
                        else if(ds.child(NodeNames.STATE2).getValue().toString().equals(fromS))
                        {
                            if(ds.child(NodeNames.CITY2).getValue().toString().equals(fromC))
                            {
                                if(ds.child(NodeNames.STATE22).getValue().toString().equals(toS))
                                {
                                    if(ds.child(NodeNames.CITY22).getValue().toString().equals(toC))
                                    {
                                        driversModelClasses.add(new DriversModelClass(userId, ds.child(NodeNames.NAME).getValue().toString(), ds.child(NodeNames.NUMBER).getValue().toString(),
                                                ds.child(NodeNames.STATUS).getValue().toString(),fromS, fromC, toS, toC));
                                    }
                                }
                            }
                        }
                        else if(ds.child(NodeNames.STATE3).getValue().toString().equals(fromS))
                        {
                            if(ds.child(NodeNames.CITY3).getValue().toString().equals(fromC))
                            {
                                if(ds.child(NodeNames.STATE33).getValue().toString().equals(toS))
                                {
                                    if(ds.child(NodeNames.CITY33).getValue().toString().equals(toC))
                                    {
                                        driversModelClasses.add(new DriversModelClass(userId, ds.child(NodeNames.NAME).getValue().toString(), ds.child(NodeNames.NUMBER).getValue().toString(),
                                                ds.child(NodeNames.STATUS).getValue().toString(),fromS, fromC, toS, toC));
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DriversListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.GONE);
    }
}



