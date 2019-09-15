package com.whiskey.tutor_ji;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signup extends AppCompatActivity {


    GoogleSignInClient mGooglesigninClient;
    SignInButton signinbutton;
    int RC_SIGN_IN = 100;
    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button signup;
    String email_string,uid;
    String pass_string;
    TextView login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        signup = findViewById(R.id.signup_button);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
//        user=mAuth.getCurrentUser();
//        uid=user.getUid();
//
//        ref= FirebaseDatabase.getInstance().getReference("learner");

        mGooglesigninClient = GoogleSignIn.getClient(this, gso);

//         if(user!=null){
//
//             ref.addValueEventListener(new ValueEventListener() {
//                 @Override
//                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                 }
//
//                 @Override
//                 public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                 }
//             });
//
//
//         }

        signinbutton = (SignInButton) findViewById(R.id.googlesignin);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               create_account();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });



    }


    void create_account(){

        email_string = email.getText().toString();
        pass_string = password.getText().toString();
        if(TextUtils.isEmpty(email_string))
            Toast.makeText(Signup.this, "Please Enter email", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(pass_string))
            Toast.makeText(Signup.this, "Please Enter password", Toast.LENGTH_SHORT).show();


        mAuth.createUserWithEmailAndPassword(email_string, pass_string)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Intent i=new Intent(Signup.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }




    private void signin() {
        Intent signInintent = mGooglesigninClient.getSignInIntent();
        startActivityForResult(signInintent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i=new Intent(Signup.this,MainActivity.class);
                            startActivity(i);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
