package com.androlord.studentapp.Funtions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androlord.studentapp.Adapter.MarksSheetAdapter;
import com.androlord.studentapp.Data.SubjectMarks;
import com.androlord.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMarks extends AppCompatActivity {
    RecyclerView recyclerView;
    MarksSheetAdapter adapter;
    ArrayList<SubjectMarks> list=new ArrayList<SubjectMarks>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_marks);
        Intent intent=getIntent();
        if(intent.hasExtra("studentcode"))
        {
            String studentcode=intent.getStringExtra("studentcode");
            init(studentcode);
        }
    }

    private void init(String studentcode) {
        recyclerView=findViewById(R.id.marksheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewMarks.this));
        adapter =new MarksSheetAdapter(list);
        recyclerView.setAdapter(adapter);
        setdata(studentcode);
    }
    private void setdata(final String student) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students/"+student+"/Courses");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    getMarks(dataSnapshot1.getKey(),student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getMarks(String key, String student) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("Courses/"+key+"/Students/"+student+"/Exams");
        final SubjectMarks subjectMarks=new SubjectMarks(new ArrayList<String>(),new ArrayList<Long>());
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    subjectMarks.examnames.add(dataSnapshot1.getKey());
                    subjectMarks.examscores.add((Long)dataSnapshot1.getValue());
                }
                list.add(subjectMarks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
