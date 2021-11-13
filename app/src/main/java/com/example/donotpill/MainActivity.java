package com.example.donotpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
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
    private BottomSheetDialog addBottomSheetDialog;
    private SpinnerAdapter spinnerAdapter;
    private Spinner spinner_time,spinner_dist;
    int sel_time=0,sel_dist=0;
    TimePicker timePicker;
    private AlarmManager alarmManager;
    private int hour, minute;

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
                addBottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.NewDialog);
                addBottomSheetDialog.setContentView(R.layout.dialog_addbottomsheet);
                addBottomSheetDialog.setCanceledOnTouchOutside(true);
                addBottomSheetDialog.show();

                spinner_time = (Spinner) addBottomSheetDialog.findViewById(R.id.sp_time);
                spinner_dist = (Spinner) addBottomSheetDialog.findViewById(R.id.sp_dist);
                timePicker=addBottomSheetDialog.findViewById(R.id.tp_start);


                ArrayList items_time = new ArrayList<>();
                ArrayList items_dist = new ArrayList<>();

                for(int i=1;i<=6;++i){
                    items_time.add(i*30) ;
                    items_dist.add(i*5);
                }

                ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_time);
                ArrayAdapter<String> adapter_dist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_dist);
               // adapter.setDropDownViewResource(R.layout.spinner_item);

                spinner_time.setAdapter(adapter_time);
                spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sel_time= (int) items_time.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinner_dist.setAdapter(adapter_dist);
                spinner_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sel_dist= (int) items_dist.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //buttom complete
                addBottomSheetDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    regist();
                    addBottomSheetDialog.dismiss();

                    }
                });

                /*
                addBottomSheetDialog.(new Dialog_AddBottomSheet.OnButtonClickListener() {
                    @Override
                    public void onConfirmClick(Room room) {
                        rooms=preferenceManager.getRooms(getApplicationContext(),mAuth.getUid());
                        rooms.add(room);
                        preferenceManager.setRooms(getApplicationContext(),mAuth.getUid(),rooms);

                    }
                });
                */


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


    public void regist() {

        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour=timePicker.getHour();
            minute=timePicker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // 지정한 시간에 매일 알림
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, pIntent);

    }

    public void unregist() {
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }
}