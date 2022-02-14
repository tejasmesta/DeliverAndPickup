package com.example.drivemypackage.MainActivityDealers;

import android.Manifest;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivemypackage.Constants.NodeNames;
import com.example.drivemypackage.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder>  {

    private Context context;
    private List<BookingsModelClass> bookingsModelClassList;
    private DatabaseReference databaseReference;
    private DatabaseReference bookingReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentUser;
    private String phone;

    public BookingsAdapter(Context context, List<BookingsModelClass> bookingsModelClassList) {
        this.context = context;
        this.bookingsModelClassList = bookingsModelClassList;
    }

    @NonNull
    @Override
    public BookingsAdapter.BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bookings,parent,false);
        return new BookingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.BookingsViewHolder holder, int position) {

        BookingsModelClass bookingsModelClass = bookingsModelClassList.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUser = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);

        databaseReference.child(bookingsModelClass.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    System.out.println(snapshot.getKey());
                    String name = snapshot.child(NodeNames.NAME).getValue().toString();
                    phone = snapshot.child(NodeNames.NUMBER).getValue().toString();

                    System.out.println(name);

                    holder.Username.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        holder.From.setText(bookingsModelClass.getFrom());

        holder.To.setText(bookingsModelClass.getTo());

        holder.MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DriverDetails.class);
                intent.putExtra("userId",bookingsModelClass.getUserId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bookingsModelClassList.size();
    }

    public class BookingsViewHolder extends RecyclerView.ViewHolder{

        private TextView Username, From, sign, To, MoreDetails;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.bookings_list_username);
            From = itemView.findViewById(R.id.bookings_list_from);
            sign = itemView.findViewById(R.id.bookings_list_sign);
            To = itemView.findViewById(R.id.bookings_list_to);
            MoreDetails = itemView.findViewById(R.id.bookings_list_more_deets);

        }
    }
}

