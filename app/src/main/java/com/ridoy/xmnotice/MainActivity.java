package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView homegridView;
    private List<String> homelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar home_toolbar=findViewById(R.id.home_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Xm Notice");

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        homelist=new ArrayList<>();

        homelist.add("Admission Notice");
        homelist.add("Play Quiz");
        homelist.add("Check Eligibility");
        homelist.add("My Account");
        homelist.add("Give Feedback");
        homelist.add("Share");
        homegridView=findViewById(R.id.homegridviewid);

        HomeAdapter homeAdapter=new HomeAdapter(homelist);
        homegridView.setAdapter(homeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menulogoutid:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;

            default:
        }
        return true;
    }
    public void onBackPressed() {
        AlertDialog.Builder alertdialog;
        alertdialog=new AlertDialog.Builder(MainActivity.this);
        alertdialog.setIcon(R.drawable.ic_alert);
        alertdialog.setTitle("Exit");
        alertdialog.setMessage("Do You Want to Exit");
        alertdialog.setCancelable(false);

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=alertdialog.create();
        alertDialog.show();
    }
}