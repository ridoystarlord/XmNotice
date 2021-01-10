package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FreeUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<EligibilityModel> alluniversitynameslist;
    private Dialog loadingdialog;
    EligibilityUniversityNamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_user);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("FreeUserUniversitynamesList");

        Toolbar agricaltural_university_toolbar=findViewById(R.id.agricaltural_university_toolbarid);
        setSupportActionBar(agricaltural_university_toolbar);
        getSupportActionBar().setTitle("All University Names");

        recyclerView=findViewById(R.id.rvid);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        alluniversitynameslist=new ArrayList<>();

        loadingdialog=new Dialog(FreeUserActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.loading_progressbar_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        adapter=new EligibilityUniversityNamesAdapter(alluniversitynameslist);
        recyclerView.setAdapter(adapter);

        loadingdialog.show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    alluniversitynameslist.add(dataSnapshot.getValue(EligibilityModel.class));
                }
                adapter.notifyDataSetChanged();
                loadingdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FreeUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
                finish();
            }
        });

    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_alert);
        builder.setTitle("Notice");
        builder.setMessage("Do You want to get notify in you mobile phone, when any new university notice comes ? then signup.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(FreeUserActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        return builder;
    }

    @Override
    protected void onStart() {
        super.onStart();
        buildDialog(FreeUserActivity.this).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_freeuser_signup,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_freeuser_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_freeuser_signup:
                Intent intent=new Intent(FreeUserActivity.this,SignupActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    public void onBackPressed() {
        AlertDialog.Builder alertdialog;
        alertdialog=new AlertDialog.Builder(FreeUserActivity.this);
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