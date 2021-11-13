package com.example.donotpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginRegister extends AppCompatActivity {

    private EditText id_input, pw_input;
    private Button login_btn,signup_btn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        id_input=findViewById(R.id.id_txt);
        pw_input=findViewById(R.id.password_txt);

        login_btn=findViewById(R.id.login_btn);
        signup_btn=findViewById(R.id.signup_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEmail = id_input.getText().toString();
                String strPwd = pw_input.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginRegister.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginRegister.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}