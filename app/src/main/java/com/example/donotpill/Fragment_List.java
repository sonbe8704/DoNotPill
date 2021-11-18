package com.example.donotpill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class Fragment_List extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Adapter_Frag_List adapter_frag_list;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__list, container, false);
        fm= getActivity().getSupportFragmentManager();
        ft=fm.beginTransaction();

        //RecyclerView
        recyclerView  = view.findViewById(R.id.rv_frag1);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_frag_list = new Adapter_Frag_List(getRooms());
        adapter_frag_list.setOnItemListener(new Adapter_Frag_List.OnItemClickListener() {
            @Override
            public void onConfirmClick(View v, int pos) {
                //TODO: 메세지 발송하는 다이얼로그 띄우고 알람 끄기
            }

            @Override
            public void onRadioClick(int pos) {
                //TODO: 알람자체를 꺼버리고 레이아웃바꿔주기
            }

            @Override
            public void onItemClick(View v, int pos) {
                //TODO: 채팅방 들어가기
            }
        });
        recyclerView.setAdapter(adapter_frag_list);
        return view;
    }

    private ArrayList<Room> getRooms(){
        return ((MainActivity)getActivity()).getRooms();
    }
    public void notifyDataChanged(){
        adapter_frag_list.notifyDataSetChanged();
    }
    public void refresh(){
        ft.detach(this).attach(this).commit();
    }



}