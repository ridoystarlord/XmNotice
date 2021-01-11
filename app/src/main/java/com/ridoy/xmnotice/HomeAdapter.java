package com.ridoy.xmnotice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeAdapter extends BaseAdapter {

    private List<String> home_item_list;

    public HomeAdapter(List<String> home_item_list) {
        this.home_item_list = home_item_list;
    }

    @Override
    public int getCount() {
        return home_item_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;

        if (convertView==null){

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout,parent,false);

        }
        else {
            view=convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position==0){
                    Intent homeintent=new Intent(parent.getContext(),UniversityCategoryNamesActivity.class);
                    parent.getContext().startActivity(homeintent);
                }
                if (position==1){
                    Intent homeintent=new Intent(parent.getContext(),QuestionsActivity.class);
                    parent.getContext().startActivity(homeintent);
                }
                if (position==2){

                    Intent homeintent=new Intent(parent.getContext(),EligibilityActivity.class);
                    parent.getContext().startActivity(homeintent);

                }
                if (position==3){
                    Intent homeintent=new Intent(parent.getContext(),ProfileActivity.class);
                    parent.getContext().startActivity(homeintent);
                }

            }
        });


        TextView home_item_name=view.findViewById(R.id.home_item_nameid);
        home_item_name.setText(home_item_list.get(position).toString());


        return view;
    }
}
