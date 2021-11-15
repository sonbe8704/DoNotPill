package com.example.donotpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION=2;

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


                set_Spinners();


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

    private void set_Spinners(){

        sel_time=0;
        sel_dist=0;

        ArrayList items_time = new ArrayList<>();
        ArrayList items_dist = new ArrayList<>();

        for(int i=1;i<=6;++i){
            items_time.add(i*30) ;
            items_dist.add(i*5);
        }

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_time);
        ArrayAdapter<String> adapter_dist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_dist);
        // adapter.setDropDownViewResource(R.layout.spinner_item);

        //spinner time set
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

        //spinner dist set
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

    public Location getLocation(){
        final LocationManager lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location ret = null;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
            getLocation(); //이건 써도되고 안써도 되지만, 전 권한 승인하면 즉시 위치값 받아오려고 썼습니다!
        }
        else{
            String locationProvider = LocationManager.GPS_PROVIDER;
            ret = locationManager.getLastKnownLocation(locationProvider);
        }
        return ret;
    }

    public double getDistance(Location loc1, Location loc2){
        double ret = loc1.distanceTo(loc2);
        return ret;
    }
    public boolean isInBoundary(int boundary,double dist){
        if((double)boundary>=dist)return true;
        else return false;
    }

    public boolean isInTime(Room room){
        int hour = room.getHour();
        int min = room.getMin();
        int sTime = 60*hour+min-room.getBoundary();
        int eTime = 60*hour+min+room.getBoundary();
        SimpleDateFormat sdf = new SimpleDateFormat("HH",Locale.KOREA);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        hour = Integer.parseInt(sdf.format(date));
        sdf = new SimpleDateFormat("mm",Locale.KOREA);
        min = Integer.parseInt(sdf.format(date));
        hour=hour*60+min;

        boolean [] days= room.getDay();
        int mWeek = cal.get(Calendar.DAY_OF_WEEK);
        //요일다르면 우선 패스
        if(!days[mWeek])return false;
        //시간범위안에 들어오면 on
        if(sTime<=hour && eTime>=hour){
            return true;
        }
        else return false;
    }

}