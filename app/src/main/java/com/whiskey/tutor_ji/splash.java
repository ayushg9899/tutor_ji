package com.whiskey.tutor_ji;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splash extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference ref;
    String cat, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
//        uid=user.getUid();

        if(user!=null)
        {
            uid = user.getUid();
            ref= FirebaseDatabase.getInstance().getReference("users").child(uid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cat = dataSnapshot.child("category_key").getValue().toString();
                    if(cat.equals("learner"))
                    {
                        Intent i=new Intent(splash.this,tutorsearch.class);
                        startActivity(i);
                        Toast.makeText(splash.this, "learner", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent i=new Intent(splash.this,tutorsearch.class);
                        startActivity(i);
                        Toast.makeText(splash.this, "teacher", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Intent i=new Intent(splash.this,Signup.class);
            startActivity(i);
            Toast.makeText(splash.this, "learner", Toast.LENGTH_SHORT).show();
        }

    }
}
