package com.example.donotpill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO : 채널만들기
        //TODO :
        Toast.makeText(context, "알람~!!", Toast.LENGTH_SHORT).show();    // AVD 확인용
        Log.e("Alarm","알람입니다.");    // 로그 확인용
    }
}

