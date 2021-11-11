package com.example.donotpill;

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
    private OnItemClickListner onItemClicklistner;
    private ArrayList<Room> rooms;

    public Adapter_Frag_List(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_frag_list,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                onItemClicklistner.onConfirmClick(v,pos);
            }
        });
        holder.rb_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO: 내부알람 알람목록에서 없애고 최하위로 보낸다? 그리고 클릭안되게 하고
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_mon,tv_tue,tv_wed,tv_thu,tv_fri,tv_sat,tv_sun,tv_hour,tv_min,tv_title,tv_dist,tv_boundary,tv_confirm;
        private RadioButton rb_on;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mon= itemView.findViewById(R.id.tv_mon);
            tv_tue= itemView.findViewById(R.id.tv_tue);
            tv_wed= itemView.findViewById(R.id.tv_wed);
            tv_thu= itemView.findViewById(R.id.tv_thu);
            tv_fri= itemView.findViewById(R.id.tv_fri);
            tv_sat= itemView.findViewById(R.id.tv_sat);
            tv_sun= itemView.findViewById(R.id.tv_sun);
            tv_hour=itemView.findViewById(R.id.tv_hour);
            tv_min= itemView.findViewById(R.id.tv_min);
            tv_title= itemView.findViewById(R.id.tv_title);
            tv_dist= itemView.findViewById(R.id.tv_dist);
            tv_boundary =itemView.findViewById(R.id.tv_boundary);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
            rb_on = itemView.findViewById(R.id.rb_on);
        }
    }

    public interface OnItemClickListner{
        void onConfirmClick(View v,int pos);
        void onRadioClick(View v, int pos);
    }
    public void setOnItemListener(OnItemClickListner onItemClicklistner){
        this.onItemClicklistner = onItemClicklistner;
    }

}
