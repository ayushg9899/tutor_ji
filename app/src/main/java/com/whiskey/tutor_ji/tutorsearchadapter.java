package com.whiskey.tutor_ji;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tutorsearchadapter extends RecyclerView.Adapter<tutorsearchadapter.myviewholder> {

    Context context;
    String uid;
    ArrayList<tutorsearchgetter>tutorsearchgetterArrayList;

    public tutorsearchadapter(Context context, ArrayList<tutorsearchgetter> tutorsearchgetterArrayList) {
        this.context = context;
        this.tutorsearchgetterArrayList = tutorsearchgetterArrayList;
    }

    @NonNull
    @Override
    public tutorsearchadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorlist,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorsearchadapter.myviewholder holder, final int position) {
        holder.name.setText(tutorsearchgetterArrayList.get(position).getName());
        holder.mode.setText(tutorsearchgetterArrayList.get(position).getMode());
        holder.city.setText(tutorsearchgetterArrayList.get(position).getAddress_city());
        holder.subject.setText(tutorsearchgetterArrayList.get(position).getSubject());
//        holder.avgrate.setRating(tutorsearchgetterArrayList.get(position).getAvg_rate());




        FirebaseAuth mauth=FirebaseAuth.getInstance();
       final DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    final String key=ds.getKey();

                    DatabaseReference ref1=ref.child(key);
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                            if(dataSnapshot.child("name").getValue().toString().equals(tutorsearchgetterArrayList.get(position).getName())){
                                DataSnapshot dataSnapshot1= (DataSnapshot) dataSnapshot.child("reviews").getChildren();
                               String avgrate= dataSnapshot1.child("rate").getValue().toString();
                                Toast.makeText(context, ""+avgrate, Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.tutor_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            final String key=ds.getKey();

                            DatabaseReference ref1=ref.child(key);
                            ref1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                    if(dataSnapshot.child("name").getValue().toString().equals(tutorsearchgetterArrayList.get(position).getName())){
                                        uid=dataSnapshot.getKey();
                                        Intent i=new Intent(context,teacherdetails.class);
                                        i.putExtra("uid",uid);
                                         context.startActivity(i);

                                    }


                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }
        });


    }

    @Override
    public int getItemCount() {
        return tutorsearchgetterArrayList.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView name,mode,subject,city,details;
        CardView tutor_card;
        RatingBar avgrate;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tutorname);
            mode=itemView.findViewById(R.id.mode);
            subject=itemView.findViewById(R.id.tutoremail);
            details=itemView.findViewById(R.id.seemore);
            city=itemView.findViewById(R.id.tutorcity);
            tutor_card=itemView.findViewById(R.id.tutorcard);
//            avgrate=itemView.findViewById(R.id.rating);

        }
    }
}
