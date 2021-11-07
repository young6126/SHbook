package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SearchResultActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;    // '뒤로' 버튼을 클릭했을 때의 시간
    private long TIME_INTERVAL = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차를 정의
    private Toast toast;
    Button btnSell;
    EditText searchEdit;
    TextView profileText1;
    ConstraintLayout result1,result2,result3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        result1 = (ConstraintLayout)findViewById(R.id.result1);
        result2 = (ConstraintLayout)findViewById(R.id.result2);

        //검색창에 검색한 내용 유지
        Intent intent = getIntent();
        String searchhint = intent.getStringExtra("SearcName");
        searchEdit = (EditText)findViewById(R.id.searchEdittext);
        searchEdit.setText(searchhint);

        result1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
                startActivity(intent);
            }
        });
        result2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}