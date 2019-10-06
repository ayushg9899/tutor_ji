package com.whiskey.tutor_ji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView learner,teacher;
    FirebaseAuth mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        learner = findViewById(R.id.learner);
        teacher = findViewById(R.id.teacher);
        learner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Learner_form.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "learner opening", Toast.LENGTH_SHORT).show();
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Teacher_form.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Teacher opening", Toast.LENGTH_SHORT).show();
            }
        });



    }

//    @Override
//    protected void onStart() {
//        Intent i=new Intent(MainActivity.this,Signup.class);
//        startActivity(i);
//        super.onStart();
//    }
}
