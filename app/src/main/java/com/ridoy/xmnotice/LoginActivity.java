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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_edittext_phone, login_edittext_password;
    private ImageButton btn_login;
    private Button btn_signup;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        Toolbar login_toolbar=findViewById(R.id.login_toolbarid);
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setTitle("LOGIN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_edittext_phone = findViewById(R.id.login_edittext_phoneid);
        login_edittext_password = findViewById(R.id.login_edittext_passwordid);
        btn_login = findViewById(R.id.btn_loginid);
        btn_signup = findViewById(R.id.btn_signupid);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v == btn_login){
            userLogin();
        }
        if(v == btn_signup){
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }

    }

    private void userLogin() {

        final String phone = login_edittext_phone.getText().toString().trim();
        final String password = login_edittext_password.getText().toString().trim();

        if (phone.isEmpty())
        {
            login_edittext_phone.setError("Plz, Enter Your Phone Number");
            login_edittext_phone.requestFocus();
            return;
        }
        if (phone.length()<11 || phone.length()>11)
        {
            login_edittext_phone.setError("Plz, Valid Phone Number");
            login_edittext_phone.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            login_edittext_password.setError("Plz, Enter Your Password");
            login_edittext_password.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            login_edittext_password.setError("Minimum Length of password is 6");
            login_edittext_password.requestFocus();
            return;
        }

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("name"),
                                                obj.getString("phone_number"),
                                                obj.getString("sscpoint"),
                                                obj.getString("sscyear"),
                                                obj.getString("hscpoint"),
                                                obj.getString("hscyear"),
                                                obj.getInt("score")
                                        );
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", phone);
                params.put("password", password);
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