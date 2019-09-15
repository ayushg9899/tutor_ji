package com.whiskey.tutor_ji;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    EditText email;
    EditText password;
    Button signup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        signup = findViewById(R.id.signin_button);
        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_account();

            }
        });

    }

    void create_account() {

        String email_string = email.getText().toString();
        String pass_string = password.getText().toString();
        if (TextUtils.isEmpty(email_string))
            Toast.makeText(Login.this, "Please Enter email", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(pass_string))
            Toast.makeText(Login.this, "Please Enter password", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email_string, pass_string)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i=new Intent(Login.this,Learner_form.class);
                            startActivity(i);
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    }
