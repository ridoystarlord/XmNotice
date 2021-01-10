package com.ridoy.xmnotice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.viewholder> {

    private List<UnitModel> unitlist;
    private String universityname;

    public UnitAdapter(List<UnitModel> unitlist, String universityname) {
        this.unitlist = unitlist;
        this.universityname=universityname;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.setdata(unitlist.get(position).getUnitname());

    }

    @Override
    public int getItemCount() {
        return unitlist.size();
    }


    class viewholder extends RecyclerView.ViewHolder{

        private TextView unitname;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            unitname=itemView.findViewById(R.id.universitynameid);

        }
        private void setdata(final String name)
        {
            unitname.setText(name);

            final Intent intent=new Intent(itemView.getContext(),PDFViewActivity.class);
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PDFUrl").child(universityname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (name.equals("A")){
                        myRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String url=snapshot.getValue(String.class);
                                intent.putExtra("uniturl",url);
                                intent.putExtra("unit",name);
                                itemView.getContext().startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (name.equals("B")){
                        myRef.child(name).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String url=snapshot.getValue(String.class);
                                intent.putExtra("uniturl",url);
                                intent.putExtra("unit",name);
                                itemView.getContext().startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(itemView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            });
        }
    }
}
