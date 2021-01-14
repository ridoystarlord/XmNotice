package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.ridoy.xmnotice.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private GridView homegridView;
    private List<String> homelist;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar home_toolbar=findViewById(R.id.home_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Xm Notice");

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.content,new HomeFragment());
                        break;
                    case 1:
                        //transaction.replace(R.id.content,new DashboardFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.content,new ProfileFragment());
                        break;

                }
                transaction.commit();
                return false;
            }
        });
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
            case R.id.menushareid:
                Toast.makeText(this, "This is share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuratingid:
                Toast.makeText(this, "Give Us Rating", Toast.LENGTH_SHORT).show();
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