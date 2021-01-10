package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UniversityCategoryNamesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button general_university_name,engineering_university_name,agricaltural_university_name,science_technology_university_name,medical_university_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_category_names);

        Toolbar toolbar=findViewById(R.id.university_category_Names_toolbarid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("University Names");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        general_university_name=findViewById(R.id.general_university_name_btnid);
        engineering_university_name=findViewById(R.id.engineering_university_name_btnid);
        agricaltural_university_name=findViewById(R.id.agricaltural_university_name_btnid);
        science_technology_university_name=findViewById(R.id.science_technology_university_name_btnid);
        medical_university_name=findViewById(R.id.medical_university_name_btnid);

        general_university_name.setOnClickListener(this);
        engineering_university_name.setOnClickListener(this);
        agricaltural_university_name.setOnClickListener(this);
        science_technology_university_name.setOnClickListener(this);
        medical_university_name.setOnClickListener(this);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.general_university_name_btnid){

            Intent university_Categoty_Names_intent=new Intent(UniversityCategoryNamesActivity.this,GeneralUniversityActivity.class);
            startActivity(university_Categoty_Names_intent);

        }
        if (v.getId()==R.id.engineering_university_name_btnid){
            Intent university_Categoty_Names_intent=new Intent(UniversityCategoryNamesActivity.this,EngineeringUniversityActivity.class);
            startActivity(university_Categoty_Names_intent);

        }
        if (v.getId()==R.id.agricaltural_university_name_btnid){
            Intent university_Categoty_Names_intent=new Intent(UniversityCategoryNamesActivity.this,AgricalturalUniversityActivity.class);
            startActivity(university_Categoty_Names_intent);

        }
        if (v.getId()==R.id.science_technology_university_name_btnid){
            Intent university_Categoty_Names_intent=new Intent(UniversityCategoryNamesActivity.this,ScienceTechnologyUniversityActivity.class);
            startActivity(university_Categoty_Names_intent);

        }
        if (v.getId()==R.id.medical_university_name_btnid){

        }
    }
}