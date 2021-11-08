package com.example.donotpill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_List extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__list, container, false);
        //RecyclerView
        recyclerView  = view.findViewById(R.id.rv_frag1);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}