package com.whiskey.tutor_ji;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Teacher_dash extends Fragment {


    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    ImageView editphoto;
    private static final int Gallerypickup=1;
    private FirebaseAuth mauth;
    private String currentuserid;
    private Button updateprofilebutton;
    private EditText editnamev,editemailv,editcontactnov, gender_edit, class_edit;
    private EditText city_edit, state_edit, qualify_edit, exp_edit, professione_edit, mode_edit;
    private StorageReference userprofileimagesref;
    private Uri imageuri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_dash2, container, false);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        currentuserid=mauth.getUid();
        database=FirebaseDatabase.getInstance();

        ref=database.getReference("users").child(currentuserid);

        userprofileimagesref= FirebaseStorage.getInstance().getReference("uploads");

        editphoto=(ImageView)view. findViewById(R.id.image);
        editnamev=(EditText) view.findViewById(R.id.name_edit);
        editemailv=(EditText) view.findViewById(R.id.email_edit);
        editemailv.setEnabled(false);
        updateprofilebutton=(Button) view.findViewById(R.id.update_photo);
        editcontactnov=(EditText) view.findViewById(R.id.contact_edit);
        city_edit=(EditText) view.findViewById(R.id.city_edit);
        state_edit=(EditText) view.findViewById(R.id.state_edit);
        professione_edit=(EditText) view.findViewById(R.id.profession_edit);
        exp_edit=(EditText) view.findViewById(R.id.exp_edit);
        qualify_edit=(EditText) view.findViewById(R.id.qualify_edit);
        mode_edit=(EditText) view.findViewById(R.id.mode_edit);



        class_edit = view.findViewById(R.id.class_edit);
//        school_edit = view.findViewById(R.id.school_edit);
        gender_edit = view.findViewById(R.id.gender_edit);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String clas=dataSnapshot.child("class").getValue().toString();
                String gender=dataSnapshot.child("gender").getValue().toString();
//                String school=dataSnapshot.child("school").getValue().toString();
                String email= dataSnapshot.child("email").getValue().toString();
                String city= dataSnapshot.child("address_city").getValue().toString();
                String state= dataSnapshot.child("address_State").getValue().toString();
                String qualify= dataSnapshot.child("qualification").getValue().toString();
                String prof= dataSnapshot.child("profession").getValue().toString();
                String exp= dataSnapshot.child("experience").getValue().toString();
                String mod= dataSnapshot.child("mode").getValue().toString();


                editnamev.setText(name);
//                school_edit.setText(school);
                gender_edit.setText(gender);
                class_edit.setText(clas);
                editemailv.setText(email);
                city_edit.setText(city);
                state_edit.setText(state);
                qualify_edit.setText(qualify);
                exp_edit.setText(exp);
                professione_edit.setText(prof);
                mode_edit.setText(mod);

                if(dataSnapshot.child("image").getValue().toString().isEmpty()){
                    editphoto.setImageResource(R.drawable.ic_launcher_background);

                }

                else{

                    String uri = (dataSnapshot.child("image").getValue().toString());
                    if(!uri.isEmpty()) {
                        Picasso.get().load(uri).into(editphoto);
//                        Toast.makeText(getContext(), "Profile Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(getActivity(), "" + uri.toString(), Toast.LENGTH_SHORT).show();
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
        return view;
    }

    private void updatesettings() {

        String setname=editnamev.getText().toString();
        String setcontact=editcontactnov.getText().toString();

        if(TextUtils.isEmpty(setname)){
            Toast.makeText(getActivity(), "Please Write Your Name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setcontact)){
            Toast.makeText(getActivity(), "Please Give Your Contact number", Toast.LENGTH_SHORT).show();
        }
        else{
            ref.child(currentuserid).child("name").setValue(setname);
            ref.child(currentuserid).child("contactnumber").setValue(setcontact);
//            startActivity(new Intent(Learner_dashboard.this,homepage.class));

        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
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
//                            Toast.makeText(getActivity(), ""+downloadurl, Toast.LENGTH_SHORT).show();


                        }
                    });

                }
            });

        }


    }

}
