package com.ridoy.xmnotice;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    private TextView points;
    private EditText bkashnumber,cashoutpoint;
    private Button requestbutton;
    FirebaseDatabase database;
    ProgressDialog dialog;
    int finalpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_wallet, container, false);

        database=FirebaseDatabase.getInstance();
        dialog=new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Request Sending...");

        points=v.findViewById(R.id.score_screen_scoreid);
        bkashnumber=v.findViewById(R.id.bkashnumberbox);
        cashoutpoint=v.findViewById(R.id.cashoutpoints);
        requestbutton=v.findViewById(R.id.reset_btn);

        points.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUsercurrentScore()));


        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=bkashnumber.getText().toString();
                String point=cashoutpoint.getText().toString();

                if (number.isEmpty())
                {
                    bkashnumber.setError("Plz, Enter Your Bkash Number");
                    bkashnumber.requestFocus();
                    return;
                }
                if (point.isEmpty())
                {
                    cashoutpoint.setError("Plz, Enter Your Cashout Point");
                    cashoutpoint.requestFocus();
                    return;
                }
                if(Integer.parseInt(point)>SharedPrefManager.getInstance(getContext()).getUsercurrentScore()){
                    cashoutpoint.setError("You Dont have that much point. Plz, Enter Your valid Cashout Point");
                    cashoutpoint.requestFocus();
                    return;
                }
                dialog.show();
                if (SharedPrefManager.getInstance(getContext()).getUsercurrentScore()>=1000){

                    CashoutRequest cashoutRequest=new CashoutRequest(number,point);
                    database.getReference().child("Payment Request").child(SharedPrefManager.getInstance(getContext()).getUserphone())
                            .setValue(cashoutRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            saveeditdetails();
                            dialog.dismiss();
                        }
                    });
                    
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "You need more coins to get withdraw.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return v;
    }
    private void saveeditdetails() {

        final String amount=cashoutpoint.getText().toString();
        int currentpoint=SharedPrefManager.getInstance(getContext()).getUsercurrentScore();
        finalpoint=currentpoint-Integer.parseInt(amount);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_WALLETREQUEST, new Response.Listener<String>() {
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
                params.put("currentscore", String.valueOf(finalpoint));
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(getContext(),finalpoint);
        sharedPrefManager.updateUserScore();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }
}