package com.ridoy.xmnotice;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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


public class PasswordResetFragment extends Fragment {


    public PasswordResetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private EditText currentpassword,newpassword;
    private Button resetbtn;
    //private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_password_reset, container, false);

        currentpassword=v.findViewById(R.id.currentpassword);
        newpassword=v.findViewById(R.id.newpassword);
        resetbtn=v.findViewById(R.id.reset_btn);

//        dialog=new ProgressDialog(getContext());
//        dialog.setCancelable(false);
//        dialog.setMessage("Please Wait...\nPassword Resetting...");

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveeditdetails();

            }
        });


        return v;
    }

    private void saveeditdetails() {

        final String pre_pass=currentpassword.getText().toString();
        final String new_pass=newpassword.getText().toString();

        if (pre_pass.isEmpty())
        {
            currentpassword.setError("Plz, Enter Your Password");
            currentpassword.requestFocus();
            return;
        }
        if (new_pass.isEmpty())
        {
            newpassword.setError("Plz, Enter Your New Password");
            newpassword.requestFocus();
            return;
        }

        if (new_pass.length()<6)
        {
            newpassword.setError("Minimum Length of password is 6");
            newpassword.requestFocus();
            return;
        }
        //dialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_RESETPASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").toString().equals("1")){
                        Toast.makeText(getContext(), "Password Update Successful", Toast.LENGTH_LONG).show();
                    }
                    else if (jsonObject.getString("message").toString().equals("2")){
                        Toast.makeText(getContext(), "Failed to Update Try again", Toast.LENGTH_SHORT).show();
                    }
                    else if (jsonObject.getString("message").toString().equals("3")){
                        Toast.makeText(getContext(), "Invalid Current password", Toast.LENGTH_SHORT).show();

                            currentpassword.setError("Plz, Enter Your valid Current Password");
                            currentpassword.requestFocus();
                            return;
                    }
                    else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(SharedPrefManager.getInstance(getContext()).getUserId()));
                params.put("phone_number", SharedPrefManager.getInstance(getContext()).getUserphone());
                params.put("currentpass", pre_pass);
                params.put("newpass", new_pass);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }

}