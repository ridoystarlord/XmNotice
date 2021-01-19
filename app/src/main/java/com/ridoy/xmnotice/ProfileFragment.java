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
        private Button edit,save;

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
        edit=v.findViewById(R.id.buttonedit);
        save=v.findViewById(R.id.buttonsave);


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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

                username.setFocusableInTouchMode(true);
                username.setFocusable(true);
                username.requestFocus();
                sscpoint.setFocusableInTouchMode(true);
                sscpoint.setFocusable(true);
                sscyear.setFocusableInTouchMode(true);
                sscyear.setFocusable(true);
                hscpoint.setFocusableInTouchMode(true);
                hscpoint.setFocusable(true);
                hscyear.setFocusableInTouchMode(true);
                hscyear.setFocusable(true);

                InputMethodManager inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(username,InputMethodManager.SHOW_IMPLICIT);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveeditdetails();

                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);

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

            }
        });

        return v;

    }
    private void saveeditdetails() {

        final String name=this.username.getText().toString().trim();

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
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(getContext(),name);
        sharedPrefManager.updateUserName();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }
}