package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity{

    private EditText signup_edittext_name;
    private Button btn_signup;
    private ProgressDialog progressDialog;
    private EditText signup_sscpoint,signup_sscyear,signup_hscpoint,signup_hscyear;
    private CircleImageView imageView;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ProgressDialog dialog;
    Uri selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Updating...");

        signup_edittext_name = findViewById(R.id.signup_edittext_nameid);
        signup_sscpoint = findViewById(R.id.signup_edittext_sscpointid);
        signup_sscyear =findViewById(R.id.signup_edittext_sscyearid);
        signup_hscpoint=findViewById(R.id.signup_edittext_hscpointid);
        signup_hscyear=findViewById(R.id.signup_edittext_hscyearid);
        imageView=findViewById(R.id.signup_circleimageviewid);
        btn_signup =  findViewById(R.id.btn_signup_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        final String name = signup_edittext_name.getText().toString().trim();
        final String sscpoint = signup_sscpoint.getText().toString().trim();
        final String sscyear = signup_sscyear.getText().toString().trim();
        final String hscpoint = signup_hscpoint.getText().toString().trim();
        final String hscyear = signup_hscyear.getText().toString().trim();

        if (name.isEmpty())
        {
            signup_edittext_name.setError("Plz, Enter Your Name");
            signup_edittext_name.requestFocus();
            return;
        }
        if (sscpoint.isEmpty())
        {
            signup_sscpoint.setError("Plz, Enter Your SSC Point");
            signup_sscpoint.requestFocus();
            return;
        }
        if (sscyear.isEmpty())
        {
            signup_sscyear.setError("Plz, Enter Your SSC YEAR");
            signup_sscyear.requestFocus();
            return;
        }
        if (hscpoint.isEmpty())
        {
            signup_hscpoint.setError("Plz, Enter Your HSC Point");
            signup_hscpoint.requestFocus();
            return;
        }

        if (hscyear.isEmpty())
        {
            signup_hscyear.setError("Plz, Enter Your HSC YEAR");
            signup_hscyear.requestFocus();
            return;
        }
        dialog.show();
        if (selectedImage!=null){
            final StorageReference reference=storage.getReference().child("ProfileImages").child(auth.getUid());
            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageURL=uri.toString();
                                String uid=auth.getUid();
                                String phone=auth.getCurrentUser().getPhoneNumber();

                                UserInformation userInformation=new UserInformation(uid,name,imageURL,sscpoint,sscyear,hscpoint,hscyear,"0","0","0");
                                database.getReference().child("Users")
                                        .child(uid)
                                        .setValue(userInformation)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialog.dismiss();
                                                Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        });

                    }
                }
            });
        }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data !=null){
            if (data.getData() !=null){
                imageView.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }

    //    private void registerUser() {
//
//        final String phone = signup_edittext_phone.getText().toString().trim();
//        final String name = signup_edittext_name.getText().toString().trim();
//        final String password = signup_edittext_password.getText().toString().trim();
//        final String sscpoint = signup_sscpoint.getText().toString().trim();
//        final String sscyear = signup_sscyear.getText().toString().trim();
//        final String hscpoint = signup_hscpoint.getText().toString().trim();
//        final String hscyear = signup_hscyear.getText().toString().trim();
//
//        if (name.isEmpty())
//        {
//            signup_edittext_name.setError("Plz, Enter Your Name");
//            signup_edittext_name.requestFocus();
//            return;
//        }
//
//        if (phone.isEmpty())
//        {
//            signup_edittext_phone.setError("Plz, Enter Your Phone Number");
//            signup_edittext_phone.requestFocus();
//            return;
//        }
//        if (phone.length()<11 || phone.length()>11)
//        {
//            signup_edittext_phone.setError("Plz, Valid Phone Number");
//            signup_edittext_phone.requestFocus();
//            return;
//        }
//        if (password.isEmpty())
//        {
//            signup_edittext_password.setError("Plz, Enter Your Password");
//            signup_edittext_password.requestFocus();
//            return;
//        }
//
//        if (password.length()<6)
//        {
//            signup_edittext_password.setError("Minimum Length of password is 6");
//            signup_edittext_password.requestFocus();
//            return;
//        }
//        if (sscpoint.isEmpty())
//        {
//            signup_sscpoint.setError("Plz, Enter Your SSC Point");
//            signup_sscpoint.requestFocus();
//            return;
//        }
//        if (sscyear.isEmpty())
//        {
//            signup_sscyear.setError("Plz, Enter Your SSC YEAR");
//            signup_sscyear.requestFocus();
//            return;
//        }
//        if (hscpoint.isEmpty())
//        {
//            signup_hscpoint.setError("Plz, Enter Your HSC Point");
//            signup_hscpoint.requestFocus();
//            return;
//        }
//
//        if (hscyear.isEmpty())
//        {
//            signup_hscyear.setError("Plz, Enter Your HSC YEAR");
//            signup_hscyear.requestFocus();
//            return;
//        }
//
//        progressDialog.setMessage("Registering user...");
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                progressDialog.dismiss();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                    if (jsonObject.getString("message").equals("0")){
//                        Toast.makeText(SignupActivity.this, "This Phone number is already exists", Toast.LENGTH_SHORT).show();
//
//                            signup_edittext_phone.setError("Plz, Enter a valid Phone Number");
//                            signup_edittext_phone.requestFocus();
//                            return;
//                    }
//                    else {
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//                    SharedPrefManager.getInstance(getApplicationContext()).usersignup(name,phone,sscpoint,sscyear,hscpoint,hscyear,0);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                progressDialog.hide();
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name);
//                params.put("phone_number", phone);
//                params.put("password", password);
//                params.put("sscpoint", sscpoint);
//                params.put("sscyear", sscyear);
//                params.put("hscpoint", hscpoint);
//                params.put("hscyear", hscyear);
//                return params;
//            }
//        };
//        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId()== android.R.id.home)
//        {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}