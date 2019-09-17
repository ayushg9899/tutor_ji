package com.whiskey.tutor_ji;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Learner_dashboard extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    ImageView editphoto;
    private static final int Gallerypickup=1;
    private FirebaseAuth mauth;
    private String currentuserid;
    private Button updateprofilebutton;
    private EditText editnamev,editemailv,editcontactnov, gender_edit, class_edit, school_edit;

    private StorageReference userprofileimagesref;
    private Uri imageuri;
//    private StorageTask uploadtask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_dashboard);

        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        currentuserid=mauth.getUid();
        database=FirebaseDatabase.getInstance();

        ref=database.getReference("users").child(currentuserid);

        userprofileimagesref= FirebaseStorage.getInstance().getReference("uploads");

        editphoto=(ImageView) findViewById(R.id.image);
        editnamev=(EditText) findViewById(R.id.name_edit);
        editemailv=(EditText) findViewById(R.id.email_edit);
        editemailv.setEnabled(false);
        updateprofilebutton=(Button) findViewById(R.id.update_photo);
        editcontactnov=(EditText) findViewById(R.id.contact_edit);

        class_edit = findViewById(R.id.class_edit);
        school_edit = findViewById(R.id.school_edit);
        gender_edit = findViewById(R.id.gender_edit);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String clas=dataSnapshot.child("class").getValue().toString();
                String gender=dataSnapshot.child("gender").getValue().toString();
                String school=dataSnapshot.child("school").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                editnamev.setText(name);
                school_edit.setText(school);
                gender_edit.setText(gender);
                class_edit.setText(clas);
                editemailv.setText(email);


                if(dataSnapshot.child("image").getValue().toString().isEmpty()){
                    editphoto.setImageResource(R.drawable.ic_launcher_background);

                }

                else{

                    String uri = (dataSnapshot.child("image").getValue().toString());
                    if(!uri.isEmpty()) {
                        Picasso.get().load(uri).into(editphoto);
                        Toast.makeText(Learner_dashboard.this, "Profile Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                        Toast.makeText(Learner_dashboard.this, "" + uri.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        updateprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatesettings();
            }
        });


        editphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent=new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,Gallerypickup);
            }
        });
    }

    private void updatesettings() {

        String setname=editnamev.getText().toString();
        String setcontact=editcontactnov.getText().toString();

        if(TextUtils.isEmpty(setname)){
            Toast.makeText(this, "Please Write Your Name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setcontact)){
            Toast.makeText(this, "Please Give Your Contact number", Toast.LENGTH_SHORT).show();
        }
        else{
            ref.child(currentuserid).child("name").setValue(setname);
            ref.child(currentuserid).child("contactnumber").setValue(setcontact);
//            startActivity(new Intent(Learner_dashboard.this,homepage.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==Gallerypickup && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();


            final StorageReference filepath=userprofileimagesref.child(currentuserid + ".jpg");

            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            final String downloadurl=task.getResult().toString();
                            ref.child("image").setValue(downloadurl);
                            Toast.makeText(Learner_dashboard.this, ""+downloadurl, Toast.LENGTH_SHORT).show();


                        }
                    });

                }
            });

        }
//
//
//
    }

}