package com.androlord.studentapp.Funtions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androlord.studentapp.Adapter.AttendanceSheetAdapter;
import com.androlord.studentapp.Data.AttendaceRecord;
import com.androlord.studentapp.MainActivity;
import com.androlord.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAttendence extends AppCompatActivity {
    TextView textView;
    AttendanceSheetAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<AttendaceRecord> list=new ArrayList<AttendaceRecord>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);
        Intent intent=getIntent();
        if(intent.hasExtra("studentcode"))
        {
            String studentcode=intent.getStringExtra("studentcode");
            init(studentcode);
        }



    }

    private void init(String student) {

        recyclerView=findViewById(R.id.attendencesheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendence.this));

        adapter=new AttendanceSheetAdapter(list);
        recyclerView.setAdapter(adapter);
        setdata(student);

    }

    private void setdata(final String student) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students/"+student+"/Courses");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    getAttendence(dataSnapshot1.getKey(),student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAttendence(final String key, final String student) {
        //Toast.makeText(getApplicationContext(),key,Toast.LENGTH_LONG).show();
        Log.d("ak47", "getAttendence: "+key);
        final AttendaceRecord record=new AttendaceRecord(key,-1,-1);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("Courses/"+key+"/Total");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                record.total=(Long) dataSnapshot.getValue();
                insert(record);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference myRef2 = database.getReference("Courses/"+key+"/Students/"+student+"/Present");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                record.present=(Long) dataSnapshot.getValue();
                insert(record);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void insert(AttendaceRecord record) {
        Log.d("ak47", "insert: "+record);

        if(record.present!=-1&&record.total!=-1)
        {
            int flag=-1;
            for (int i=0;i<list.size();i++)
            {
                if(record.coursecode.equalsIgnoreCase(list.get(i).coursecode))
                {
                    list.get(i).total=record.total;
                    list.get(i).present=record.present;
                    adapter.notifyItemChanged(i);
                    flag=0;
                }
            }
            if (flag==-1)
            {
                list.add(record);
                adapter.notifyItemInserted(list.size()-1);
            }
        }
    }
}
