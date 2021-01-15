package com.ridoy.xmnotice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

        private EditText username,sscpoint,sscyear,hscpoint,hscyear;
        private TextView userphone;
        private Button logoutbtn,edit,save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        username=v.findViewById(R.id.username);
        sscpoint=v.findViewById(R.id.usersscpoint);
        sscyear=v.findViewById(R.id.usersscyear);
        hscpoint=v.findViewById(R.id.userhscpoint);
        hscyear=v.findViewById(R.id.userhscyear);
        userphone=v.findViewById(R.id.userphonenumber);
        logoutbtn=v.findViewById(R.id.logoutbtn);
        edit=v.findViewById(R.id.editbtn);
        save=v.findViewById(R.id.savebtn);

        username.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        sscpoint.setText(SharedPrefManager.getInstance(getContext()).getUsersscpoint());
        sscyear.setText(SharedPrefManager.getInstance(getContext()).getUsersscyear());
        hscpoint.setText(SharedPrefManager.getInstance(getContext()).getUserhscpoint());
        hscyear.setText(SharedPrefManager.getInstance(getContext()).getUserhscyear());
        userphone.setText(SharedPrefManager.getInstance(getContext()).getUserphone());

        username.setFocusableInTouchMode(false);
        username.setFocusable(false);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getContext()).logout();
                getActivity().finish();
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setFocusableInTouchMode(true);
                edit.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveeditdetails();
                save.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                username.setFocusableInTouchMode(false);
                username.setFocusable(false);

            }
        });


        return v;

    }
    private void saveeditdetails() {

        final String name=username.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_SAVEUSERName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(SharedPrefManager.getInstance(getContext()).getUserId()));
                params.put("name", name);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(getContext(),name);
        sharedPrefManager.updateUserName();

        getActivity().finish();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        //startActivity(getIntent());

    }
}