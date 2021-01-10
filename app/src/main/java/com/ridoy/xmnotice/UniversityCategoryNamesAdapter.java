package com.ridoy.xmnotice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UniversityCategoryNamesAdapter extends RecyclerView.Adapter<UniversityCategoryNamesAdapter.viewholder> {

    private List<UniversityCategoryNamesModel> universitynameslist;

    public UniversityCategoryNamesAdapter(List<UniversityCategoryNamesModel> universitynameslist) {
        this.universitynameslist = universitynameslist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.university_category_name_items,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.setdata(universitynameslist.get(position).getImageurl(),universitynameslist.get(position).getUniversityname());

    }

    @Override
    public int getItemCount() {
        return universitynameslist.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView universityname;

        public viewholder(@NonNull final View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.circleimageviewid);
            universityname=itemView.findViewById(R.id.universitynameid);

        }
        private void setdata(String url, final String name)
        {
            Glide.with(itemView.getContext()).load(url).into(circleImageView);
            universityname.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),UnitActivity.class);
                    intent.putExtra("uniname",name);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
