package com.example.donotpill;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dialog_AddBottomSheet extends Dialog {
    private TimePicker tp_start;
    private EditText et_title;
    private Spinner sp_dist,sp_time;
    private SpinnerAdapter spinnerAdapter;
    private TextView tv_confirm,tv_cancle;
    private RadioButton rb_mon,rb_tue,rb_wed,rb_thu,rb_fri,rb_sat,rb_sun;
    private OnButtonClickListener listener;
    private FirebaseFirestore mStore;
    public Dialog_AddBottomSheet(@NonNull Context context) {
        super(context);
    }

    public Dialog_AddBottomSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addbottomsheet);
        mStore = FirebaseFirestore.getInstance();
        et_title = findViewById(R.id.et_title);
        tp_start = findViewById(R.id.tp_start);
        sp_dist=findViewById(R.id.sp_dist);
        sp_time=findViewById(R.id.sp_time);
        rb_mon=findViewById(R.id.rb_mon);
        rb_tue=findViewById(R.id.rb_tue);
        rb_wed=findViewById(R.id.rb_wed);
        rb_thu=findViewById(R.id.rb_thu);
        rb_fri=findViewById(R.id.rb_fri);
        rb_sat=findViewById(R.id.rb_sat);
        rb_sun=findViewById(R.id.rb_sun);
        tv_confirm=findViewById(R.id.tv_confirm);
        tv_cancle=findViewById(R.id.tv_cancle);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String id = mStore.collection("Rooms").document().getId();
                int hour = tp_start.getHour();
                int min = tp_start.getMinute();
                int boundary = sp_time.getSelectedItemPosition()+1;
                boolean [] day = new boolean[7];
                day[0]=rb_mon.isChecked();
                day[1]=rb_thu.isChecked();
                day[2]=rb_wed.isChecked();
                day[3]=rb_thu.isChecked();
                day[4]=rb_fri.isChecked();
                day[5]=rb_sat.isChecked();
                day[6]=rb_sun.isChecked();
                int dist = sp_dist.getSelectedItemPosition()+1;
                Room room = new Room(id,title,hour,min,boundary,day,dist,true);
                mStore.collection("Rooms").document(id).set(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onConfirmClick(room);
                        dismiss();
                    }
                });
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    public interface OnButtonClickListener{
        void onConfirmClick(Room room);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){this.listener = listener;}
}
