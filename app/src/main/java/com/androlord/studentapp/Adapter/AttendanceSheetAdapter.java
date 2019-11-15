package com.androlord.studentapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.AttendaceRecord;
import com.androlord.studentapp.R;

import java.util.ArrayList;

public class AttendanceSheetAdapter extends RecyclerView.Adapter<AttendanceSheetAdapter.MyVIewHolder>{
    @NonNull
    ArrayList<AttendaceRecord> list;
    public AttendanceSheetAdapter(ArrayList<AttendaceRecord> list)
    {
        this.list=list;
    }

    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_attendence_record,parent,false);
        MyVIewHolder vIewHolder=new MyVIewHolder(v);
        return vIewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
        // to be heavily changed
        holder.present.setText("Present:-"+String.valueOf(list.get(position).present));
        holder.total.setText("Total:-"+String.valueOf(list.get(position).total));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {
        TextView present,total;
        public MyVIewHolder(@NonNull View itemView) {
            super(itemView);
            present=itemView.findViewById(R.id.presentnumberAttendeceRecord);
            total=itemView.findViewById(R.id.TotalnumberAttendeceRecord);
        }
    }
}
