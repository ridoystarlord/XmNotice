package com.ridoy.xmnotice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    private TextView points;
    private EditText bkashnumber,cashoutpoint;
    private Button requestbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_wallet, container, false);

        points=v.findViewById(R.id.currentCoins);
        bkashnumber=v.findViewById(R.id.bkashnumberbox);
        cashoutpoint=v.findViewById(R.id.cashoutpoints);
        requestbutton=v.findViewById(R.id.sentrequest);

        points.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserScore()));


        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=bkashnumber.getText().toString();
                String point=points.getText().toString();
                
                if (SharedPrefManager.getInstance(getContext()).getUserScore()>50000){

                    Toast.makeText(getContext(), "Underdevelopment", Toast.LENGTH_SHORT).show();
                    
                }
                else {
                    Toast.makeText(getContext(), "You need more coins to get withdraw.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return v;
    }
}