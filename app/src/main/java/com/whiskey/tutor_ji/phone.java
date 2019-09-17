package com.whiskey.tutor_ji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class phone extends AppCompatActivity {


    private Button sendverificationcodebutton, verifyPhoneNumber;
    private EditText inputphonenumber, inputverificationcode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private EditText editTextMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);


//        mAuth= FirebaseAuth.getInstance();
//
//        sendverificationcodebutton = (Button) findViewById(R.id.phoneauth);
//        verifyPhoneNumber = (Button) findViewById(R.id.verify);
//        inputphonenumber = (EditText) findViewById(R.id.editphonenumber);
//        inputverificationcode = (EditText) findViewById(R.id.entercode);
//
//        sendverificationcodebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                String phoneNumber = inputphonenumber.getText().toString();
//                if (TextUtils.isEmpty(phoneNumber)) {
//                    Toast.makeText(phone.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                            "+91" +phoneNumber,        // Phone number to verify
//                            60,                 // Timeout duration
//                            TimeUnit.SECONDS,   // Unit of timeout
//                            phone.this,               // Activity (for callback binding)
//                            mCallbacks);        // OnVerificationStateChangedCallbacks
//
//                }
//            }
//        });
//
//        verifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                sendverificationcodebutton.setVisibility(View.INVISIBLE);
//                inputphonenumber.setVisibility(View.INVISIBLE);
//
//                String verificationcode= inputverificationcode.getText().toString();
//                if (TextUtils.isEmpty(verificationcode)) {
//                    Toast.makeText(phone.this, "Enter code please", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationcode);
//                    signInWithPhoneAuthCredential(credential);
//
//                }
//
//
//            }
//        });
//
//        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(phone.this, "galat hai", Toast.LENGTH_SHORT).show();
//                sendverificationcodebutton.setVisibility(View.VISIBLE);
//                inputphonenumber.setVisibility(View.VISIBLE);
//
//                verifyPhoneNumber.setVisibility(View.INVISIBLE);
//                inputverificationcode.setVisibility(View.INVISIBLE);
//
//            }
//
//            public void onCodeSent(@NonNull String verificationId,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//
//
//                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;
//                sendverificationcodebutton.setVisibility(View.INVISIBLE);
//                inputphonenumber.setVisibility(View.INVISIBLE);
//
//                verifyPhoneNumber.setVisibility(View.VISIBLE);
//                inputverificationcode.setVisibility(View.VISIBLE);
//
//                Toast.makeText(phone.this, "Code Has been sent", Toast.LENGTH_SHORT).show();
//
//                // ...
//            }
//        };


        editTextMobile = findViewById(R.id.editphonenumber);

        findViewById(R.id.phoneauth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(phone.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }

        });
    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//
//                            Toast.makeText(phone.this, "Congrats,You are logged in", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = task.getResult().getUser();
//                            Intent i=new Intent(phone.this,Learner_form.class);
//                            startActivity(i);
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//
//                                Toast.makeText(phone.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
}
