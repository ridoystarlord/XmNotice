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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EligibilityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<EligibilityModel> eligibleuniversitynameslist;
    private Dialog loadingdialog;
    EligibilityUniversityNamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("FreeUserUniversitynamesList");

        Toolbar general_university_toolbar=findViewById(R.id.eligibility_toolbarid);
        setSupportActionBar(general_university_toolbar);
        getSupportActionBar().setTitle("Eligible University Names");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.rvid);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        eligibleuniversitynameslist=new ArrayList<>();

        loadingdialog=new Dialog(EligibilityActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.loading_progressbar_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);



        adapter=new EligibilityUniversityNamesAdapter(eligibleuniversitynameslist);
        recyclerView.setAdapter(adapter);

        loadingdialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Float U_sscpoint=Float.valueOf(SharedPrefManager.getInstance(EligibilityActivity.this).getUsersscpoint());
                int U_sscyear=Integer.valueOf(SharedPrefManager.getInstance(EligibilityActivity.this).getUsersscyear());
                Float U_hscpoint=Float.valueOf(SharedPrefManager.getInstance(EligibilityActivity.this).getUserhscpoint());
                int U_hscyear=Integer.valueOf(SharedPrefManager.getInstance(EligibilityActivity.this).getUserhscyear());


                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    EligibilityModel model = dataSnapshot.getValue(EligibilityModel.class);

                    int sscyear=model.getR_sscyear();
                    int hscyear=model.getR_hscyear();
                    Float sscpoint=model.getR_sscpoint();
                    Float hscpoint=model.getR_hscpoint();

                    if ((U_hscyear==hscyear && U_sscyear==sscyear) || (U_hscyear==(hscyear-1) && U_sscyear==(sscyear-1))){
                        if (U_sscpoint>=sscpoint && U_hscpoint>=hscpoint) {
                            eligibleuniversitynameslist.add(dataSnapshot.getValue(EligibilityModel.class));
                        }

                    }

                }
                if (eligibleuniversitynameslist.isEmpty()){
                    buildDialog(EligibilityActivity.this).show();
                }
                else {

                    adapter.notifyDataSetChanged();
                    loadingdialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EligibilityActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_alert);
        builder.setTitle("Not Eligible");
        builder.setMessage("You are not Eligible for any university");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eligible,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_eligible_search);
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

}