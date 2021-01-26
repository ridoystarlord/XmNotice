package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ProfileActivity extends AppCompatActivity {

    private EditText username,sscpoint,sscyear,hscpoint,hscyear;
    private TextView userphone,userpoint;
    private Button logoutbtn;
    private Menu action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar home_toolbar=findViewById(R.id.home_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Xm Notice");

        username=findViewById(R.id.username);
        sscpoint=findViewById(R.id.usersscpoint);
        sscyear=findViewById(R.id.usersscyear);
        hscpoint=findViewById(R.id.userhscpoint);
        hscyear=findViewById(R.id.userhscyear);
        userphone=findViewById(R.id.userphonenumber);
        logoutbtn=findViewById(R.id.logoutbtn);
        userpoint=findViewById(R.id.userpoint);

        username.setFocusableInTouchMode(false);
        username.setFocusable(false);
        sscpoint.setFocusableInTouchMode(false);
        sscpoint.setFocusable(false);
        sscyear.setFocusableInTouchMode(false);
        sscyear.setFocusable(false);
        hscpoint.setFocusableInTouchMode(false);
        hscpoint.setFocusable(false);
        hscyear.setFocusableInTouchMode(false);
        hscyear.setFocusable(false);

        username.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsername());
        sscpoint.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsersscpoint());
        sscyear.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsersscyear());
        hscpoint.setText(SharedPrefManager.getInstance(getApplicationContext()).getUserhscpoint());
        hscyear.setText(SharedPrefManager.getInstance(getApplicationContext()).getUserhscyear());
        userphone.setText(SharedPrefManager.getInstance(getApplicationContext()).getUserphone());
        userpoint.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUsercurrentScore()));

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                finish();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);

        action=menu;
        action.findItem(R.id.menu_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:

                username.setFocusableInTouchMode(true);
                username.setFocusable(true);
                sscpoint.setFocusableInTouchMode(true);
                sscpoint.setFocusable(true);
                sscyear.setFocusableInTouchMode(true);
                sscyear.setFocusable(true);
                hscpoint.setFocusableInTouchMode(true);
                hscpoint.setFocusable(true);
                hscyear.setFocusableInTouchMode(true);
                hscyear.setFocusable(true);

                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(username,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                break;
            case R.id.menu_save:

                saveeditdetails();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                username.setFocusableInTouchMode(false);
                username.setFocusable(false);
                sscpoint.setFocusableInTouchMode(false);
                sscpoint.setFocusable(false);
                sscyear.setFocusableInTouchMode(false);
                sscyear.setFocusable(false);
                hscpoint.setFocusableInTouchMode(false);
                hscpoint.setFocusable(false);
                hscyear.setFocusableInTouchMode(false);
                hscyear.setFocusable(false);

                break;

            default:
        }
        return true;
    }

    private void saveeditdetails() {

        final String name=this.username.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_SAVEUSERName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId()));
                params.put("name", name);
                return params;
            }
        };
        MySingleton.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext(),name);
        sharedPrefManager.updateUserName();

        finish();
        startActivity(getIntent());

    }
}