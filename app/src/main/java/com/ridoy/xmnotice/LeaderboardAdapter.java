package com.ridoy.xmnotice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<User> {

    Context context;
    List<User> list;


    public LeaderboardAdapter(@NonNull Context context, List<User> list) {
        super(context, R.layout.test, list);
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test,null,true);

        TextView postion=view.findViewById(R.id.index);
        TextView name=view.findViewById(R.id.name);
        TextView score=view.findViewById(R.id.score);

        postion.setText(String.valueOf(position+1));
        name.setText(list.get(position).getFname());
        score.setText(list.get(position).getFcoins());


        return view;
    }
}
