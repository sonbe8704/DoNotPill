package com.example.donotpill;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class Dialog_AddBottomSheet extends Dialog {
    private TimePicker tp_start;
    private EditText et_title;
    private Spinner sp_dist,sp_time;
    private SpinnerAdapter spinnerAdapter;
    private TextView tv_confirm,tv_cancle;
    private RadioButton rb_mon,rb_tue,rb_wed,rb_thu,rb_fri,rb_sat,rb_sun;
    private OnButtonClickListener listener;
    private FirebaseFirestore mStore;
    int sel_time=0,sel_dist=0;

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
        set_Spinners();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String id = mStore.collection("Rooms").document().getId();
                int hour = tp_start.getHour();
                int min = tp_start.getMinute();
                int boundary = sp_time.getSelectedItemPosition()+1;
                ArrayList<Boolean> day = new ArrayList<Boolean>();
                day.add(0,rb_mon.isChecked());
                day.add(1,rb_tue.isChecked());
                day.add(2,rb_wed.isChecked());
                day.add(3,rb_thu.isChecked());
                day.add(4,rb_fri.isChecked());
                day.add(5,rb_sat.isChecked());
                day.add(6,rb_sun.isChecked());
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

    private void set_Spinners(){

        sel_time=0;
        sel_dist=0;

        ArrayList items_time = new ArrayList<>();
        ArrayList items_dist = new ArrayList<>();

        for(int i=1;i<=6;++i){
            items_time.add(i*30) ;
            items_dist.add(i*5);
        }

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items_time);
        ArrayAdapter<String> adapter_dist = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items_dist);
        // adapter.setDropDownViewResource(R.layout.spinner_item);

        //spinner time set
        sp_time.setAdapter(adapter_time);
        sp_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sel_time= (int) items_time.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner dist set
        sp_time.setAdapter(adapter_dist);
        sp_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sel_dist= (int) items_dist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
