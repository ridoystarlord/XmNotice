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
        private TextView userphone,userpoint;

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
        userpoint=v.findViewById(R.id.userpoint);


        username.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        sscpoint.setText(SharedPrefManager.getInstance(getContext()).getUsersscpoint());
        sscyear.setText(SharedPrefManager.getInstance(getContext()).getUsersscyear());
        hscpoint.setText(SharedPrefManager.getInstance(getContext()).getUserhscpoint());
        hscyear.setText(SharedPrefManager.getInstance(getContext()).getUserhscyear());
        userphone.setText(SharedPrefManager.getInstance(getContext()).getUserphone());
        userpoint.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserScore()));

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

        return v;

    }
}