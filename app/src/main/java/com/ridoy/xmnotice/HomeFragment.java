package com.ridoy.xmnotice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridoy.xmnotice.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding binding;
    private List<String> homelist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        homelist=new ArrayList<>();
        homelist.add("Admission Notice");
        homelist.add("Play Quiz");
        homelist.add("Check\nEligibility");

        HomeAdapter homeAdapter=new HomeAdapter(homelist);
        binding.homegridviewid.setAdapter(homeAdapter);

        return binding.getRoot();
    }
}