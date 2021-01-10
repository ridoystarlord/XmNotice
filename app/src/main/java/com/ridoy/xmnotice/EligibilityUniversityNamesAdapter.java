package com.ridoy.xmnotice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EligibilityUniversityNamesAdapter extends RecyclerView.Adapter<EligibilityUniversityNamesAdapter.viewholder> implements Filterable {

    private List<EligibilityModel> universitynameslist;
    private List<EligibilityModel> filteruniversitynameslist;

    public EligibilityUniversityNamesAdapter(List<EligibilityModel> universitynameslist) {
        this.universitynameslist = universitynameslist;
        this.filteruniversitynameslist=universitynameslist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.university_category_name_items,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        String name=filteruniversitynameslist.get(position).getUniversityname();
        String url=filteruniversitynameslist.get(position).getImageurl();

        holder.setdata(url,name);

    }

    @Override
    public int getItemCount() {
        return filteruniversitynameslist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charecter=constraint.toString();
                if (charecter.isEmpty()){
                    filteruniversitynameslist=universitynameslist;

                }else {
                    List<EligibilityModel> filterlist=new ArrayList<>();
                    for (EligibilityModel model: universitynameslist){
                        if (model.getUniversityname().toLowerCase().contains(charecter.toLowerCase())){
                            filterlist.add(model);
                        }
                    }
                    filteruniversitynameslist=filterlist;
                }
                FilterResults results=new FilterResults();
                results.values=filteruniversitynameslist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteruniversitynameslist= (ArrayList<EligibilityModel>) results.values;
                notifyDataSetChanged();

            }
        };
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
