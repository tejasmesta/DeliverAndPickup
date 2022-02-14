package com.example.drivemypackage.MainActivityDealers;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class DealersAdapter extends RecyclerView.Adapter<DealersAdapter.DealersViewHolder>  {

    private Context context;
    private List<DriversModelClass> driversModelClassList;
    private DatabaseReference databaseReference;
    private DatabaseReference bookingReference;
    private DatabaseReference bookedReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentUser;

    public DealersAdapter(Context context, List<DriversModelClass> driversModelClassList) {
        this.context = context;
        this.driversModelClassList = driversModelClassList;
    }


    @NonNull
    @Override
    public DealersAdapter.DealersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.drivers_list,parent,false);
        return new DealersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DealersAdapter.DealersViewHolder holder, int position) {

        DriversModelClass driversModelClass = driversModelClassList.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUser = firebaseUser.getUid();

        holder.Name.setText(driversModelClass.getName());

        databaseReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.DRIVERS);
        bookingReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.BOOKING);
        bookedReference = FirebaseDatabase.getInstance("https://drivemypackage-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(NodeNames.BOOKED);

        holder.Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map = new HashMap<>();


                map.put(NodeNames.BOOKEDBY,currentUser);
                map.put(NodeNames.FROM,driversModelClass.getFromC()+","+driversModelClass.getFromS());
                map.put(NodeNames.TO,driversModelClass.getToC()+","+driversModelClass.getToS());


                bookingReference.child(driversModelClass.getUserId()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            DatabaseReference pushRef = bookedReference.child(currentUser).push();
                            String id = pushRef.getKey();
                            HashMap<String,String> map1 = new HashMap<>();
                            map1.put(NodeNames.BOOKEDWHOM,driversModelClass.getUserId());
                            map1.put(NodeNames.FROM,driversModelClass.getFromC()+","+driversModelClass.getFromS());
                            map1.put(NodeNames.TO,driversModelClass.getToC()+","+driversModelClass.getToS());
                            pushRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful())
                                    {
                                        databaseReference.child(driversModelClass.getUserId()).child(NodeNames.STATUS).setValue("booked").addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task1) {
                                                if(task1.isSuccessful())
                                                {
                                                    holder.Book.setText("Booked");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        holder.MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DriverDetails.class);
                intent.putExtra("userId",driversModelClass.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return driversModelClassList.size();
    }

    public class DealersViewHolder extends RecyclerView.ViewHolder{

        private TextView Name;
        private Button Book, MoreDetails;

        public DealersViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.drivers_list_username);
            Book = itemView.findViewById(R.id.drivers_list_book_button);
            MoreDetails = itemView.findViewById(R.id.drivers_list_moredetails_button);
        }
    }
}
