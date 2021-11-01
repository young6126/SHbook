package com.example.shbook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스

    private EditText mEtEmail, mEtPwd, mEtAge, mEtMainAdress, mEtMainAdress2, mEtFavorite; //회원가입 입력필드
    private Button mBtnRegister; //회원가입 버튼
    private Spinner spinner1;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000; // 주소 요청코드 상수 request

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        spinner1 = (Spinner)findViewById(R.id.spinner);
        final ArrayList<String> list = new ArrayList<>();
        list.add("추리");
        list.add("공포");
        list.add("스릴러");
        list.add("과학");
        list.add("판타지");
        list.add("무협");
        list.add("게임");
        list.add("로맨스");

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, list);
        spinner1.setAdapter(spinnerAdapter);
        String spinner_text = spinner1.getSelectedItem().toString();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Bada-books-user");

        mEtEmail = findViewById(R.id.signinID); //아이디
        mEtPwd = findViewById(R.id.signinID2); //비밀번호
        mEtAge = findViewById(R.id.signinID3); //나이
        mEtMainAdress = findViewById(R.id.signinID4); //api text 주소검색
        mEtMainAdress2 = findViewById(R.id.signinID5); //상세주소


        //성별,주소,선호장르 지정해야함.
        mBtnRegister = findViewById(R.id.nextBtn);
        //view 띄워줄 launcher 생성

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String data = intent.getExtras().getString("data");
                            if (data != null) {
                                mEtMainAdress.setText(data);
                            }
                        }
                    }
                });


        mEtMainAdress.setFocusable(false); // 메인주소 클릭 안되게 막음.

        mEtMainAdress.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //화면전환 애니메이션 없애기
                overridePendingTransition(0, 0);

                //주소결과
                Intent i = new Intent(getApplicationContext(), AddressApiActivity.class);
                i.putExtra("requestCode", SEARCH_ADDRESS_ACTIVITY);
                launcher.launch(i);
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //성별,주소,선호장르 지정해야함.
                                                mBtnRegister = findViewById(R.id.nextBtn);

                                                mBtnRegister.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View view) {
                                                                                        //회원가입 처리 시작
                                                                                        String strEmail = mEtEmail.getText().toString();
                                                                                        String strPwd = mEtPwd.getText().toString();
                                                                                        String strAge = mEtAge.getText().toString().trim();
                                                                                        String strAddress1 = mEtMainAdress.getText().toString();
                                                                                        String strAddress2 = mEtMainAdress2.getText().toString();
                                                                                        String strAddress = strAddress1 + strAddress2;
                                                                                        String strFavorite = spinner_text.toString();
                                                                                        //Firebase Auth  진행
                                                                                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                                                                                    UserAccount account = new UserAccount();
                                                                                                    account.setIdToken(firebaseUser.getUid()); //Uid 는 고유값
                                                                                                    account.setEmailId(firebaseUser.getEmail());
                                                                                                    account.setPassword(strPwd);
                                                                                                    account.setAge(strAge);
                                                                                                    account.setAddress(strAddress);
                                                                                                    account.setFavorite(strFavorite);
                                                                                                    //setValue : database에 insert 하는 행위
                                                                                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                                                                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                                                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                                                    startActivity(intent);
                                                                                                } else {
                                                                                                    Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }

                                                );
                                            }
                                        });
    }
}

