package com.whiskey.tutor_ji;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tutorsearch extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private TextView progress1, progress2;
    private RecyclerView recyclerView;

    ArrayList<tutorsearchgetter> list = new ArrayList<>();
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorsearch);






            recyclerView = (RecyclerView) findViewById(R.id.recyclercourses);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            uid = user.getUid();
            database = FirebaseDatabase.getInstance();
            ref = FirebaseDatabase.getInstance().getReference("users");
            ref.keepSynced(true);



            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                    if(dataSnapshot.child("category_key").getValue().toString()=="teacher"){

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        list.add(ds.getValue(tutorsearchgetter.class));
                    }

//                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    tutorsearchadapter adp = new tutorsearchadapter(getApplicationContext(), list);
                    recyclerView.setAdapter(adp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
    }

