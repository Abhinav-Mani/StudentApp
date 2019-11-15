package com.androlord.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androlord.studentapp.Authentication.LoginActivity;
import com.androlord.studentapp.Funtions.ViewAttendence;
import com.androlord.studentapp.Funtions.ViewMarks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String studentcode="";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Logout:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else
            Toast.makeText(MainActivity.this,"Welcome"+currentUser.getEmail(),Toast.LENGTH_LONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check();

    }

    private void check() {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else
        {
            init();
            tempcode();
        }
            Toast.makeText(MainActivity.this,"Welcome"+currentUser.getEmail(),Toast.LENGTH_LONG);
    }

    private void tempcode() {

        Button b=findViewById(R.id.view_attendence),b2=findViewById(R.id.view_marks);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentcode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, ViewAttendence.class);
                    intent.putExtra("studentcode",studentcode);
                    startActivity(intent);
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentcode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, ViewMarks.class);
                    intent.putExtra("studentcode",studentcode);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email="";
        email = mAuth.getCurrentUser().getEmail().toString();
        DatabaseReference myRef = database.getReference("StudentList/"+email.substring(0,email.indexOf('@')));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentcode=(String) dataSnapshot.getValue();

                startExtraction((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void startExtraction(String key) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Students/"+key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("ak47",dataSnapshot.getKey()+"-->> "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
