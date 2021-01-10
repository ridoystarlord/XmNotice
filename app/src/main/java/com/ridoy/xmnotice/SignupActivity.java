package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signup_edittext_phone, signup_edittext_password, signup_edittext_name;
    private Button btn_login;
    private ProgressDialog progressDialog;
    private ImageButton btn_signin;
    private EditText signup_sscpoint,signup_sscyear,signup_hscpoint,signup_hscyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        Toolbar signup_toolbar=findViewById(R.id.signup_toolbarid);
        setSupportActionBar(signup_toolbar);
        getSupportActionBar().setTitle("SIGN UP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signup_edittext_name = findViewById(R.id.signup_edittext_nameid);
        signup_edittext_phone = findViewById(R.id.signup_edittext_phoneid);
        signup_edittext_password =findViewById(R.id.signup_edittext_passwordid);

        signup_sscpoint=findViewById(R.id.signup_sscpointid);
        signup_sscyear=findViewById(R.id.signup_sscyeartid);
        signup_hscpoint=findViewById(R.id.signup_hscpointid);
        signup_hscyear=findViewById(R.id.signup_hscyearid);

        btn_signin =  findViewById(R.id.signupid);
        btn_login =  findViewById(R.id.btn_signinid);

        progressDialog = new ProgressDialog(this);

        btn_login.setOnClickListener(this);
        btn_signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btn_signin) {
            registerUser();
        }
        if(v == btn_login){

            startActivity(new Intent(this,LoginActivity.class));

        }

    }

    private void registerUser() {

        final String phone = signup_edittext_phone.getText().toString().trim();
        final String name = signup_edittext_name.getText().toString().trim();
        final String password = signup_edittext_password.getText().toString().trim();
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

        if (phone.isEmpty())
        {
            signup_edittext_phone.setError("Plz, Enter Your Phone Number");
            signup_edittext_phone.requestFocus();
            return;
        }
        if (phone.length()<11 || phone.length()>11)
        {
            signup_edittext_phone.setError("Plz, Valid Phone Number");
            signup_edittext_phone.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            signup_edittext_password.setError("Plz, Enter Your Password");
            signup_edittext_password.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            signup_edittext_password.setError("Minimum Length of password is 6");
            signup_edittext_password.requestFocus();
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

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.getString("message").equals("0")){
                        Toast.makeText(SignupActivity.this, "This Phone number is already exists", Toast.LENGTH_SHORT).show();

                            signup_edittext_phone.setError("Plz, Enter a valid Phone Number");
                            signup_edittext_phone.requestFocus();
                            return;
                    }
                    else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("phone_number", phone);
                params.put("password", password);
                params.put("sscpoint", sscpoint);
                params.put("sscyear", sscyear);
                params.put("hscpoint", hscpoint);
                params.put("hscyear", hscyear);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}