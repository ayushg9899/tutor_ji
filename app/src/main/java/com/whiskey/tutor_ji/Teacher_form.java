package com.whiskey.tutor_ji;

import android.Manifest;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

public class Teacher_form extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    Spinner spinner1, exp_spinner, prof_spin, qual_spin;
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
    String uid, st_name="", email, clas="";
    int arr[] ={0,0,0, 0,0,0, 0,0,0, 0,0,0};


    Button btnLookup;
    List<Item> items;
    boolean[] mode = new boolean[2];
    ListView listView;
    ItemsListAdapter myItemsListAdapter;


    HashMap<Object, String> hashMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        uid=user.getUid();

        name = (EditText) findViewById(R.id.name);
        school_name = (EditText) findViewById(R.id.school);
        submit = findViewById(R.id.submit);
        email = user.getEmail();





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
//        prof_spin = (Spinner) findViewById(R.id.prof_spin);

        // Spinner click listener
        spinner1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Teacher_form.this);
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



//        spinner1 = (Spinner) findViewById(R.id.spinner1);
        prof_spin = (Spinner) findViewById(R.id.prof_spin);

        // Spinner click listener
        prof_spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Teacher_form.this);
        // Spinner Drop down elements
        List<String> prof_exp = new ArrayList<String>();
        prof_exp.add("-SELECT-");
        prof_exp.add("TUTOR");
        prof_exp.add("TEACHER");
        prof_exp.add("STUDENT");
        prof_exp.add("other");
        // Creating adapter for spinner
        ArrayAdapter<String> prof_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prof_exp){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }
        };
        // Drop down layout style - list view with radio button
        prof_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        prof_spin.setAdapter(prof_dataAdapter);












        qual_spin = (Spinner) findViewById(R.id.qual_spin);
        // Spinner click listener
        qual_spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Teacher_form.this);
        // Spinner Drop down elements
        List<String> qual_cat = new ArrayList<String>();
        qual_cat.add("Select-");
        qual_cat.add("2nd PUC");
        qual_cat.add("Bachelor of Architecture (B.Arch.)");
        qual_cat.add("Bachelor of Arts (B.A.)");
        qual_cat.add("Bachelor of Ayurvedic Medicine & Surgery (B.A.M.S.)");
        qual_cat.add("Bachelor of Business Administration (B.B.A.)");
        qual_cat.add("Bachelor of Commerce (B.Com.)");
        qual_cat.add("Bachelor of Computer Applications (B.C.A.)");
        qual_cat.add("Bachelor of Computer Science (B.Sc. (Computer Science))");
        qual_cat.add("Bachelor of Dental Surgery (B.D.S.)");
        qual_cat.add("Bachelor of Design (B.Des. - B.D.)");
        qual_cat.add("Bachelor of Education (B.Ed.)");
        qual_cat.add("Bachelor of Engineering (B.E.)");
        qual_cat.add("Bachelor of Technology (B.Tech.)");
        qual_cat.add("Bachelor of Fine Arts (BFA - BVA)");
        qual_cat.add("Bachelor of Fisheries Science (B.F.Sc. - B.Sc. (Fisheries))");
        qual_cat.add("Bachelor of Home Science (B.A. - B.Sc. (Home Science))");
        qual_cat.add("Bachelor of Homeopathic Medicine and Surgery (B.H.M.S.)");
        qual_cat.add("Bachelor of Laws (L.L.B.)");
        qual_cat.add("Bachelor of Library Science (B.Lib. - B.Lib.Sc.)");
        qual_cat.add("Bachelor of Mass Communications (B.M.C. - B.M.M.)");
        qual_cat.add("Bachelor of Medicine and Bachelor of Surgery (M.B.B.S.)");
        qual_cat.add("Bachelor of Nursing (B.Sc. (Nursing))");
        qual_cat.add("Bachelor of Pharmacy (B.Pharm.)");
        qual_cat.add("Bachelor of Physical Education (B.P.Ed.)");
        qual_cat.add("Bachelor of Physiotherapy (B.P.T.)");
        qual_cat.add("Bachelor of Science (B.Sc.)");
        qual_cat.add("Bachelor of Social Work (BSW or B.A. (SW))");
        qual_cat.add("Bachelor of Veterinary Science & Animal Husbandry (B.V.Sc.)");
        qual_cat.add("Doctor of Medicine (M.D.)");
        qual_cat.add("Doctor of Medicine in Homoeopathy (M.D. (Homoeopathy))");
        qual_cat.add("Master in Home Science (M.A. - M.Sc. (Home Science))");
        qual_cat.add("Master of Architecture (M.Arch.)");
        qual_cat.add("Master of Arts (M.A.)");
        qual_cat.add("Master of Business Administration (M.B.A.)");
        qual_cat.add("Master of Chirurgiae (M.Ch.)");
        qual_cat.add("Master of Commerce (M.Com.)");
        qual_cat.add("Master of Computer Applications (M.C.A.)");
        qual_cat.add("Master of Dental Surgery (M.D.S.)");
        qual_cat.add("Master of Design (M.Des. - M.Design.)");
        qual_cat.add("Master of Education (M.Ed.)");
        qual_cat.add("Master of Engineering - Master of Technology (M.E./M.Tech.)");
        qual_cat.add("Master of Fine Arts (MFA - MVA)");
        qual_cat.add("Master of Fishery Science (M.F.Sc. - M.Sc. (Fisheries))");
        qual_cat.add("Master of Laws (L.L.M.)");
        qual_cat.add("Master of Library Science (M.Lib. - M.Lib.Sc.)");
        qual_cat.add("Master of Mass Communications (M.M.C - M.M.M.)");
        qual_cat.add("Master of Pharmacy (M.Pharm)");
        qual_cat.add("Master of Philosophy (M.Phil.)");
        qual_cat.add("Master of Physical Education (M.P.Ed. - M.P.E.)");
        qual_cat.add("Master of Physiotherapy (M.P.T.)");
        qual_cat.add("Master of Science (M.Sc.)");
        qual_cat.add("Master of Science in Agriculture (M.Sc. (Agriculture))");
        qual_cat.add("Master of Social Work (M.S.W. or M.A. (SW))");
        qual_cat.add("Master of Surgery (M.S.)");
        qual_cat.add("Master of Veterinary Science (M.V.Sc.)");
        qual_cat.add("Doctor of Pharmacy (Pharm.D)");
        qual_cat.add("Doctor of Philosophy (Ph.D.)");
        qual_cat.add("Doctorate of Medicine (D.M.)");
        qual_cat.add("Master's in Environmental Pollution");
        qual_cat.add("BPT - Bachelors in Physiothreapist");
        qual_cat.add("Masters in Physiotherapist");
        qual_cat.add("Master in Food & Nutrition");
        qual_cat.add("MSc in Nutrition & Dietetics");
        qual_cat.add("BNYS - Bachelor of Naturopathy and Yogic Sciences");
        qual_cat.add("BHMS - Bachelors of Homeopathic Medicine & Surgery");
        qual_cat.add("M Phil in Food Service Management & Dietitian");
        qual_cat.add("BAMS - Bachelor in Ayurveda, Medicine & Surgery");
        qual_cat.add("Post Graduation Diploma in Dietetics, Health & Nutrition");
        qual_cat.add("Masters in Psychology");
        qual_cat.add("PhD in Psychology");
        qual_cat.add("Diploma in Psychological Medicine");
        qual_cat.add("M Phil in Clinical Psychology");
        qual_cat.add("Post Graduation in Psychology");
        qual_cat.add("MPC - Masters in Psychotherapy & Counselling");
        qual_cat.add("Other");
        qual_cat.add("MSc in Chemistry");



        // Creating adapter for spinner
        ArrayAdapter<String> qual_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, qual_cat){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }
        };
        // Drop down layout style - list view with radio button
        qual_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        qual_spin.setAdapter(qual_dataAdapter);
//        spinner2.setId(23);





        final String[] class_category = {"-class-","1","2","3","4","5","6","7","8","9","10","11","12"};


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<stateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < class_category.length; i++) {
            stateVO stateVO = new stateVO();
            stateVO.setTitle(class_category[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        for(int i=0;i<12;i++)
            arr[i]=0;

        Myadapter myAdapter = new Myadapter(Teacher_form.this, 0,
                listVOs, arr);

        spinner.setAdapter(myAdapter);
//        myAdapter.notifyDataSetChanged();


//        Toast.makeText(this, ""+clas, Toast.LENGTH_SHORT).show();



        exp_spinner = (Spinner) findViewById(R.id.exp_spin);
        // Spinner click listener
        exp_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Teacher_form.this);
        // Spinner Drop down elements
        List<String> class_categories = new ArrayList<String>();
        class_categories.add("-SELECT-");
        class_categories.add("1");
        class_categories.add("2");
        class_categories.add("3");
        class_categories.add("4");
        class_categories.add("5");
        class_categories.add("6");
        class_categories.add("7");
        class_categories.add("8");
        class_categories.add("9");
        class_categories.add("10-20");
        class_categories.add("20-25");
        class_categories.add("25+");


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
        exp_spinner.setAdapter(class_dataAdapter);


















        mAuth=FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_name=name.getText().toString();
                int flag=0;
//                st_school=school_name.getText().toString();
                Toast.makeText(Teacher_form.this, "name="+st_name, Toast.LENGTH_SHORT).show();
                hashMap.put("name", st_name);
//                hashMap.put("school", st_school);
                hashMap.put("image","");
                clas="";
                for(int i=0;i<12;i++)
                {
                    if(arr[i]==1)
                    {
                        clas= clas + Integer.toString(i+1) + " ";

                    }
                }
                hashMap.put("class", clas);
                hashMap.put("category_key","teacher");
                hashMap.put("email",email);

               if(mode[0]==true&&mode[1]==true)
                   hashMap.put("mode","both");
               else if(mode[0]==false&&mode[1]==false) {
                   flag = 1;
                   Toast.makeText(Teacher_form.this, "enter the mode of teaching", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   String mod = (mode[0])?"One on one/Private Tuition":"Group Classes";
                   hashMap.put("mode", mod);
               }

                String str[] ={"name", "class", "gender", "experience", "profession", "qualification", "address_city"};
                for(int i=0;i<str.length;i++) {
                    if(!hashMap.containsKey(str[i]))
                    {
                        Toast.makeText(Teacher_form.this, "pls input "+str[i], Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;
                    }
                    else if(hashMap.get(str[i]).equals(""))
                    {
                        Toast.makeText(Teacher_form.this, "pls input "+str[i], Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;
                    }
                }
                if(flag==0) {
                    Toast.makeText(Teacher_form.this, "Form uploaded successfully", Toast.LENGTH_SHORT).show();
                    ref.child(uid).setValue(hashMap);
                    Intent i = new Intent(Teacher_form.this, Teacher_main.class);
                    startActivity(i);
                    finish();
                }

            }
        });










        listView = (ListView)findViewById(R.id.listview);
//        btnLookup = (Button)findViewById(R.id.lookup);

        initItems();
        myItemsListAdapter = new ItemsListAdapter(this, items);
        listView.setAdapter(myItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Teacher_form.this,
                        ((Item)(parent.getItemAtPosition(position))).ItemString,
                        Toast.LENGTH_LONG).show();
            }});

//        btnLookup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String str = "Check items:\n";
//
//                for (int i=0; i<items.size(); i++){
//                    if (items.get(i).isChecked()){
//                        str += i + "\n";
//                    }
//                }
//
//                /*
//                int cnt = myItemsListAdapter.getCount();
//                for (int i=0; i<cnt; i++){
//                    if(myItemsListAdapter.isChecked(i)){
//                        str += i + "\n";
//                    }
//                }
//                */
//
//                Toast.makeText(Teacher_form.this,
//                        str,
//                        Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

    private void initItems(){
        items = new ArrayList<Item>();
            ArrayList <String> arrayText=new ArrayList<>();
            arrayText.add("One on one/Private Tuition");
            arrayText.add("Group CLasses");
//        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for(int i=0; i<arrayText.size(); i++){
            // Drawable d = arrayText.getDrawable(i);
            String s = arrayText.get(i);
            boolean b = false;
            Item item = new Item( s, b);
            items.add(item);
        }

        // arrayDrawable.recycle();
//        arrayText.recycle();


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
        Toast.makeText(Teacher_form.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
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
            case R.id.exp_spin:
                hashMap.put("experience", item);
                break;
            case R.id.prof_spin:
                hashMap.put("profession",item);
                break;
            case R.id.qual_spin:
                hashMap.put("qualification",item);
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










    public class Item {
        boolean checked;
        // Drawable ItemDrawable;
        String ItemString;
        Item(String t, boolean b){
            //     ItemDrawable = drawable;
            ItemString = t;
            checked = b;
        }

        public boolean isChecked(){
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        //ImageView icon;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
                // viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            //  viewHolder.icon.setImageDrawable(list.get(position).ItemDrawable);
            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            viewHolder.text.setText(itemStr);

            viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    Toast.makeText(getApplicationContext(),
                            itemStr + "setOnClickListener\nchecked: " + newState,
                            Toast.LENGTH_LONG).show();
                    mode[position]=newState;
                }
            });

            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }


}
