package com.androlord.studentapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.AttendaceRecord;
import com.androlord.studentapp.Data.SubjectMarks;
import com.androlord.studentapp.MainActivity;
import com.androlord.studentapp.R;

import java.util.ArrayList;

public class MarksSheetAdapter extends RecyclerView.Adapter<MarksSheetAdapter.MyViewHolder>{
    @NonNull
    ArrayList<SubjectMarks> list;
    public MarksSheetAdapter(ArrayList<SubjectMarks> list){
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subject_report,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubjectMarks a=list.get(position);
        String result = "";
        for(int i=0;i<a.examscores.size();i++)
        {
            result+=a.examnames.get(i)+"->>"+a.examscores.get(i)+"\n";
        }
        holder.textView.setText(result);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.displayOfResults);
        }
    }
}
