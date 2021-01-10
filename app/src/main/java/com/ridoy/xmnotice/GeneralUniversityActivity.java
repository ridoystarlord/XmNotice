package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GeneralUniversityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UniversityCategoryNamesModel> generaluniversitynameslist;
    private Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_university);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Universitynames").child("GeneralUniversityNames");

        Toolbar general_university_toolbar=findViewById(R.id.general_university_toolbarid);
        setSupportActionBar(general_university_toolbar);
        getSupportActionBar().setTitle("General University Names");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.rvid);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        generaluniversitynameslist=new ArrayList<>();

        loadingdialog=new Dialog(GeneralUniversityActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.loading_progressbar_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        final UniversityCategoryNamesAdapter adapter=new UniversityCategoryNamesAdapter(generaluniversitynameslist);
        recyclerView.setAdapter(adapter);

        loadingdialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    generaluniversitynameslist.add(dataSnapshot.getValue(UniversityCategoryNamesModel.class));
                }
                adapter.notifyDataSetChanged();
                loadingdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GeneralUniversityActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
}