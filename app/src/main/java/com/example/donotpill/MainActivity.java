package com.example.donotpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // 자신의 방 목록, 그 방에서 교류를 가장 많이 한 사람은 SharePreference에 저장이 되어있음
    // String uid -> ArrayList<String> rooms
    // String id_room -> Other others(교류많이한사람)

    //데이터 가져오기
    //preferenceManager = new PreferenceManager();
    //data = preferenceManager.getInfo(getApplicationContext(),KEY);

    private Toolbar toolbar;
    private PreferenceManager preferenceManager;
    private ArrayList<Room> rooms;
    private FirebaseFirestore mstore;
    private FirebaseAuth mAuth;
    private Dialog_AddBottomSheet addBottomSheetDialog;
    private SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get rooms
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager();
        rooms = preferenceManager.getRooms(getApplicationContext(),mAuth.getUid());

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);//커스텀액션바사용
        actionBar.setDisplayShowTitleEnabled(false);//기본제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기 기능생성

        //Spinner
        final int[] spinner_items = new int[100];
        for(int i=0;i<spinner_items.length;i++){
            spinner_items[i]=i+1;
        }
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Collections.singletonList(spinner_items));





    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull @NotNull Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_btn_add:
                //TODO: 바텀씟
                addBottomSheetDialog = new Dialog_AddBottomSheet(getApplicationContext(),R.style.NewDialog);
                addBottomSheetDialog.setContentView(R.layout.dialog_addbottomsheet);
                addBottomSheetDialog.setOnButtonClickListener(new Dialog_AddBottomSheet.OnButtonClickListener() {
                    @Override
                    public void onConfirmClick(Room room) {
                        rooms=preferenceManager.getRooms(getApplicationContext(),mAuth.getUid());
                        rooms.add(room);
                        preferenceManager.setRooms(getApplicationContext(),mAuth.getUid(),rooms);

                    }
                });

                //TODO : 리싸이클러뷰랑 연동
                return true;

            case R.id.action_btn_search:
                //TODO:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setSpinner(){

    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}