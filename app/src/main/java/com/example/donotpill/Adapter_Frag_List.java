package com.example.donotpill;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Frag_List extends RecyclerView.Adapter<Adapter_Frag_List.CustomViewHolder> {
    private OnItemClickListener onItemClicklistener;
    private ArrayList<Room> arrayList;

    public Adapter_Frag_List(ArrayList<Room> rooms) {
        this.arrayList = rooms;
        Log.e("adapter", String.valueOf(rooms.size()));
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_frag_list,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        Log.e("adataper","??");
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Room room = arrayList.get(position);
        setViewHolder(holder,room);
        if(room.isOn()){
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    onItemClicklistener.onItemClick(v,pos);
                }
            });
            holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    onItemClicklistener.onConfirmClick(v,pos);
                }
            });

            holder.rb_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //TODO: ???????????? ?????????????????? ????????? ???????????? ?????????? ????????? ??????????????? ??????
                    int pos = holder.getAdapterPosition();
                    onItemClicklistener.onRadioClick(pos);

                }
            });
        }
        else{
            Log.e("adapter","off");
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private TextView tv_mon,tv_tue,tv_wed,tv_thu,tv_fri,tv_sat,tv_sun,tv_hour,tv_min,tv_title,tv_dist,tv_boundary,tv_confirm;
        private RadioButton rb_on;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout=itemView.findViewById(R.id.lo_item);
            this.tv_mon= itemView.findViewById(R.id.tv_mon);
            this.tv_tue= itemView.findViewById(R.id.tv_tue);
            this.tv_wed= itemView.findViewById(R.id.tv_wed);
            this.tv_thu= itemView.findViewById(R.id.tv_thu);
            this.tv_fri= itemView.findViewById(R.id.tv_fri);
            this.tv_sat= itemView.findViewById(R.id.tv_sat);
            this.tv_sun= itemView.findViewById(R.id.tv_sun);
            this.tv_hour=itemView.findViewById(R.id.tv_hour);
            this.tv_min= itemView.findViewById(R.id.tv_min);
            this.tv_title= itemView.findViewById(R.id.tv_title);
            this.tv_dist= itemView.findViewById(R.id.tv_dist);
            this.tv_boundary =itemView.findViewById(R.id.tv_boundary);
            this.tv_confirm = itemView.findViewById(R.id.tv_confirm);
            this.rb_on = itemView.findViewById(R.id.rb_on);
        }

    }
    @SuppressLint("ResourceAsColor")
    protected void setViewHolder(CustomViewHolder viewHolder, Room room){
        viewHolder.rb_on.setChecked(room.isOn());
        ArrayList<Boolean> day = room.getDay();
        if(day.get(0))viewHolder.tv_mon.setTextColor(android.R.color.black);
        if(day.get(1))viewHolder.tv_tue.setTextColor(android.R.color.black);
        if(day.get(2))viewHolder.tv_wed.setTextColor(android.R.color.black);
        if(day.get(3))viewHolder.tv_thu.setTextColor(android.R.color.black);
        if(day.get(4))viewHolder.tv_fri.setTextColor(android.R.color.black);
        if(day.get(5))viewHolder.tv_sat.setTextColor(android.R.color.holo_blue_dark);
        if(day.get(6))viewHolder.tv_sun.setTextColor(android.R.color.holo_red_dark);
        viewHolder.tv_hour.setText(room.getHour());
        viewHolder.tv_min.setText(room.getMin());
        viewHolder.tv_dist.setText(room.getDist());
        viewHolder.tv_boundary.setText(room.getBoundary());
        viewHolder.tv_title.setText(room.getTitle());

    }

    public interface OnItemClickListener{
        void onConfirmClick(View v,int pos);
        void onRadioClick(int pos);
        void onItemClick(View v,int pos);
    }
    public void setOnItemListener(OnItemClickListener onItemClicklistener){
        this.onItemClicklistener = onItemClicklistener;
    }




}
