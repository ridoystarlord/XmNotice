package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private TextView setverifyphone;
    private Button btn_Verifyotp;
    private EditText otpbox;
    String verificationId;
    FirebaseAuth auth;
    ProgressDialog progressDialog,dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        auth=FirebaseAuth.getInstance();
        String mobile=getIntent().getStringExtra("mobile");

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sending OTP...");
        progressDialog.show();

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");

        setverifyphone=findViewById(R.id.setverifyphone);
        setverifyphone.setText("Verify  "+mobile);
        btn_Verifyotp=findViewById(R.id.verifyotpbtn);
        otpbox=findViewById(R.id.otpbox);

        sentOTPCode(mobile);
        btn_Verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                verifyOTPCode(otpbox.getText().toString());

            }
        });



    }

    private void verifyOTPCode(String otpcode) {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,otpcode);
        signInprocess(credential);
    }

    private void signInprocess(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    dialog.dismiss();

                        Intent intent = new Intent(OTPActivity.this, SignupActivity.class);
                        startActivity(intent);
                        finish();

                }
                else {
                    Toast.makeText(OTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sentOTPCode(String mobile) {
        PhoneAuthOptions options= PhoneAuthOptions.newBuilder()
                .setPhoneNumber(mobile)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        String sms=phoneAuthCredential.getSmsCode();
                        verifyOTPCode(sms);
                        otpbox.setText(sms);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressDialog.dismiss();
                        Toast.makeText(OTPActivity.this, "OTP Sent Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        progressDialog.dismiss();
                        verificationId=s;
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void userLogin(final String phone) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                                                obj.getInt("currentscore"),
                                                obj.getInt("totalscore"),
                                                obj.getInt("totalearn")
                                        );
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
                return params;
            }

        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userLogin(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            Intent intent = new Intent(OTPActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}