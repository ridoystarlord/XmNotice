package com.ridoy.xmnotice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ScoreActivity extends AppCompatActivity {

    private TextView score_screen_score;
    private Button btn_done;
    int finalscore;
    ProgressDialog dialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait, Your Score Updating");
        dialog.setCancelable(false);

        score_screen_score=findViewById(R.id.score_screen_scoreid);
        btn_done=findViewById(R.id.score_screen_buttonid);

        int score=getIntent().getIntExtra("score",0);
        int previoussocre=SharedPrefManager.getInstance(getApplicationContext()).getUserScore();

        finalscore=score+previoussocre;

        final String scorestring=getIntent().getStringExtra("scorestring");
        score_screen_score.setText(scorestring);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_SAVEUSERSCORE, new Response.Listener<String>() {
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
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId()));
                        params.put("score", String.valueOf(finalscore));
                        return params;
                    }
                };
                MySingleton.getInstance(ScoreActivity.this).addToRequestQueue(stringRequest);

                SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext(),finalscore);
                sharedPrefManager.updateUserScore();

                Intent intent=new Intent(ScoreActivity.this,MainActivity.class);
                startActivity(intent);
                ScoreActivity.this.finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_SAVEUSERSCORE, new Response.Listener<String>() {
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
                params.put("score", String.valueOf(finalscore));
                return params;
            }
        };
        MySingleton.getInstance(ScoreActivity.this).addToRequestQueue(stringRequest);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext(),finalscore);
        sharedPrefManager.updateUserScore();

        Intent intent=new Intent(ScoreActivity.this,MainActivity.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }
}