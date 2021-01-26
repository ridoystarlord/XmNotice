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

public class UnitActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UnitModel> unitlist;
    private Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Units");

        Toolbar unit_toolbar=findViewById(R.id.unit_toolbarid);
        setSupportActionBar(unit_toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("uniname")+" Units");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.rvid);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final List<UnitModel> unitlist=new ArrayList<>();

        loadingdialog=new Dialog(UnitActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.loading_progressbar_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        final UnitAdapter adapter=new UnitAdapter(unitlist,getIntent().getStringExtra("uniname"));
        recyclerView.setAdapter(adapter);

        loadingdialog.show();

        myRef.child(getIntent().getStringExtra("uniname")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    unitlist.add(dataSnapshot.getValue(UnitModel.class));
                }
                if (unitlist.isEmpty()){

                    final Intent intent=new Intent(UnitActivity.this,PDFViewActivity.class);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PDFUrl").child(getIntent().getStringExtra("uniname"));
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String url=snapshot.getValue(String.class);

                            if (url==null){
                                buildDialog(UnitActivity.this).show();
                            }
                            else {
                                intent.putExtra("uniturl", url);
                                intent.putExtra("unit", getIntent().getStringExtra("uniname"));
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter.notifyDataSetChanged();
                loadingdialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UnitActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Notice");
        builder.setMessage("Not Published Yet");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                finish();
            }
        });

        return builder;
    }
}