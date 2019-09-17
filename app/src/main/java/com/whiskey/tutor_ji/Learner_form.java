package com.whiskey.tutor_ji;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Learner_form extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    Spinner spinner1, spinner2;
    EditText name, school_name;
    Button submit;


    Button getLocationBtn;
    TextView locationText;
    LocationManager locationManager;


    private Button sendverificationcodebutton, verifyPhoneNumber;
    private EditText inputphonenumber, inputverificationcode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference ref;
    String uid, st_name, st_school,email;


    HashMap<Object, String> hashMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_form);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        uid=user.getUid();
        email=user.getEmail();

        name = (EditText) findViewById(R.id.name);
        school_name = (EditText) findViewById(R.id.school);
        submit = findViewById(R.id.submit);





        ref=FirebaseDatabase.getInstance().getReference("users");





        getLocationBtn = (Button)findViewById(R.id.getlocationbtn);
        locationText = (TextView)findViewById(R.id.location);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });





        spinner1 = (Spinner) findViewById(R.id.spinner1);
        // Spinner click listener
        spinner1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Learner_form.this);
        // Spinner Drop down elements
        List<String> gender_categories = new ArrayList<String>();
        gender_categories.add("-Gender-");
        gender_categories.add("MALE");
        gender_categories.add("FEMALE");
        gender_categories.add("other");
        // Creating adapter for spinner
        ArrayAdapter<String> gender_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender_categories){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }
        };
        // Drop down layout style - list view with radio button
        gender_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner1.setAdapter(gender_dataAdapter);



        spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Spinner click listener
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Learner_form.this);
        // Spinner Drop down elements
        List<String> class_categories = new ArrayList<String>();
        class_categories.add("-CLASS-");
        class_categories.add("1");
        class_categories.add("2");
        class_categories.add("3");
        class_categories.add("4");
        class_categories.add("5");
        class_categories.add("6");
        class_categories.add("7");
        class_categories.add("8");
        class_categories.add("9");
        class_categories.add("10");
        class_categories.add("11");
        class_categories.add("12");


        // Creating adapter for spinner
        ArrayAdapter<String> class_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, class_categories){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }
        };
        // Drop down layout style - list view with radio button
        class_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(class_dataAdapter);
//        spinner2.setId(23);






        mAuth=FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_name=name.getText().toString();
                st_school=school_name.getText().toString();
                Toast.makeText(Learner_form.this, "name="+st_name+"\nschool="+st_school, Toast.LENGTH_SHORT).show();
                hashMap.put("name", st_name);
                hashMap.put("school", st_school);
                hashMap.put("image","");
                hashMap.put("email",email);
                hashMap.put("category_key", "learner");
                int flag=0;
                String str[] ={"name", "class", "gender", "school", "address_city"};
                for(int i=0;i<str.length;i++) {
                    if(!hashMap.containsKey(str[i]))
                    {
                        Toast.makeText(Learner_form.this, "pls input "+str[i], Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;
                    }
                    else if(hashMap.get(str[i]).equals(""))
                    {
                        Toast.makeText(Learner_form.this, "pls input "+str[i], Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;
                    }
                }
                if(flag==0) {
                    Toast.makeText(Learner_form.this, "Form uploaded successfully", Toast.LENGTH_SHORT).show();
                    ref.child(uid).setValue(hashMap);
                    Intent i = new Intent(Learner_form.this, Learner_dashboard.class);
                    startActivity(i);
                    finish();
                }


            }
        });


    }




    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));

            Address address = addresses.get(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(i)).append("\n");
            }
            hashMap.put("address_city",address.getLocality());
            hashMap.put("address_postal",address.getPostalCode());
            hashMap.put("address country",address.getCountryName());
            hashMap.put("address_State",address.getAdminArea());
            hashMap.put("address_sublocality",address.getSubLocality());
            hashMap.put("address_sublocality2",address.getPremises());
//            hashMap.put("address_sublocality3",address());

//            address.get

//            sb.append(address.getLocality()).append("\n");
//            sb.append(address.getPostalCode()).append("\n");
//            sb.append(address.getCountryName());
            //result = sb.toString();



        }catch(Exception e)
        {

        }

    }


    //location function

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Learner_form.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


    //spinner function


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch(parent.getId()){
            case R.id.spinner1:
                hashMap.put("gender",item);
                break;
            case R.id.spinner2:
                hashMap.put("class",item);
                break;


        }




        // Showing selected spinner item
//        if(position>0) {
//            if(item.length()>3)
//            {
////          Toast.makeText(parent.getContext(), "gender: " + item, Toast.LENGTH_LONG).show();
//                hashMap.put("gender",item);
//            }
//            else
//            {
////                Toast.makeText(parent.getContext(), "CLASS: " + item, Toast.LENGTH_LONG).show();
//                hashMap.put("class",item);
//            }
//
//        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(),"please select gender and class", Toast.LENGTH_LONG).show();
        // TODO Auto-generated method stub
    }
}
