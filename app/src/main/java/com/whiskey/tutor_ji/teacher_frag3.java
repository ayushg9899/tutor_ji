package com.whiskey.tutor_ji;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class teacher_frag3 extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference ref;
    ArrayList<reviewgetter> list = new ArrayList<>();
    RecyclerView recyclerView;


    public teacher_frag3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_frag3, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid=user.getUid();

        ref = FirebaseDatabase.getInstance().getReference("users").child(uid).child("questions");
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();



                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    list.add(ds.getValue(reviewgetter.class));
                }



                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                reviewadapter adp = new reviewadapter(getActivity().getApplicationContext(), list);
                recyclerView.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return view;


    }

}
