package com.example.drivemypackage.MainActivityDrivers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class BookedByAdapter extends RecyclerView.Adapter<BookedByAdapter.BookedByViewHolder>  {

    private Context context;
    private List<BookedByModelClass> bookedByModelClassList;
    private DatabaseReference databaseReference;
    private DatabaseReference bookingReference;
    private DatabaseReference bookedReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentUser;

    public BookedByAdapter(Context context, List<BookedByModelClass> bookedByModelClassList) {
        this.context = context;
        this.bookedByModelClassList = bookedByModelClassList;
    }


    @NonNull
    @Override
    public BookedByAdapter.BookedByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bookings,parent,false);
        return new BookedByAdapter.BookedByViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedByAdapter.BookedByViewHolder holder, int position) {

        BookedByModelClass bookedByModelClass = bookedByModelClassList.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUser = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DEALERS);

        databaseReference.child(bookedByModelClass.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name = snapshot.child(NodeNames.NAME).getValue().toString();
                    holder.username.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.from.setText(bookedByModelClass.getFrom());
        holder.to.setText(bookedByModelClass.getTo());

        holder.MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DealersDetails.class);
                intent.putExtra("userId",bookedByModelClass.getUserId());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return bookedByModelClassList.size();
    }

    public class BookedByViewHolder extends RecyclerView.ViewHolder{

        private TextView username, from, to, sign;
        private Button MoreDetails;

        public BookedByViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.bookings_list_username);
            from = itemView.findViewById(R.id.bookings_list_from);
            sign = itemView.findViewById(R.id.bookings_list_sign);
            to = itemView.findViewById(R.id.bookings_list_to);
            MoreDetails = itemView.findViewById(R.id.bookings_list_more_deets);

        }
    }
}

