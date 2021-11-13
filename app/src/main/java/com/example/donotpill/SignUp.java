package com.example.donotpill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText id_input, pw_input1, pw_input2;
    private Button complete_btn;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore mstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        id_input=findViewById(R.id.idinput_txt);
        pw_input1=findViewById(R.id.pwinput1_txt);
        pw_input2=findViewById(R.id.pwinput2_txt);
        complete_btn=findViewById(R.id.sign_complete_btn);
        Pattern pattern= Patterns.EMAIL_ADDRESS;


        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String id = id_input.getText().toString();
                String pw1 = pw_input1.getText().toString();
                 String pw2 = pw_input2.getText().toString();

                if(!pattern.matcher(id).matches())
                {
                    Toast.makeText(SignUp.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pw1.length()<6 )
                {
                    Toast.makeText(SignUp.this,"비밀번호는 6자리 이상 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.e("***",id + pw1);
                if(pw1.equals(pw2)) {

                    firebaseAuth.createUserWithEmailAndPassword(id, pw1).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        FirebaseUser user =  firebaseAuth.getCurrentUser();

                                        String user_Idtoken = user.getUid();
                                        ArrayList<String> friends= new ArrayList<>();

                                        //새로운 유저생성
                                        User newUser = new User();
                                        newUser.setId(user_Idtoken);
                                        newUser.setFriends(friends);

                                        mstore.collection("User").document(user_Idtoken).set(newUser);

                                        Toast.makeText(SignUp.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignUp.this,LoginRegister.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(SignUp.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}