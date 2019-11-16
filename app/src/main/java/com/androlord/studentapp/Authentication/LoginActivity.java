package com.androlord.studentapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androlord.studentapp.MainActivity;
import com.androlord.studentapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button SignIn;
    TextView SignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        init();
    }

    private void init() {
        email=findViewById(R.id.emailID);
        password=findViewById(R.id.Password);
        SignIn=findViewById(R.id.SignIn);
        SignUp=findViewById(R.id.GotoSignUp);

        mAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
                finish();
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString().trim())||TextUtils.isEmpty(password.getText().toString().trim()))
                {
                    Toast.makeText(LoginActivity.this,"Fill Up",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.d("ak47", "onClick: "+email.getText().toString().trim()+"->"+password.getText().toString().trim());
                    mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("ak47", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("ak47", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }

            }
        });


    }
}
