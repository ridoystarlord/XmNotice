package com.ridoy.xmnotice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<User> users;
    User listuser;
    LeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView=findViewById(R.id.mylistview);
        users = new ArrayList<>();
        adapter = new LeaderboardAdapter(this, users);
        listView.setAdapter(adapter);
        retrivedata();
    }
    public void retrivedata(){

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LEADERBOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        users.clear();
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");

                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object=jsonArray.getJSONObject(i);

                                String name=object.getString("name");
                                String score=object.getString("score");

                                listuser=new User(name,score);
                                users.add(listuser);
                                adapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LeaderboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(LeaderboardActivity.this).addToRequestQueue(stringRequest);

    }
}