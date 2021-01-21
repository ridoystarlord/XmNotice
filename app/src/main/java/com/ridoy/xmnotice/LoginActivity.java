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

public class LoginActivity extends AppCompatActivity{

    private EditText login_edittext_phone;
    private Button btn_sentotp;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_edittext_phone = findViewById(R.id.login_edittext_phoneid);
        btn_sentotp = findViewById(R.id.btn_sentotpid);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btn_sentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = login_edittext_phone.getText().toString().trim();

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

                String phonenumber="+88"+phone;

                Intent intent=new Intent(LoginActivity.this,OTPActivity.class);
                intent.putExtra("mobile",phonenumber);
                startActivity(intent);
            }
        });


    }
}