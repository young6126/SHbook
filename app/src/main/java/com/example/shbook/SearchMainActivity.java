package com.example.shbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SearchMainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;    // '뒤로' 버튼을 클릭했을 때의 시간
    private long TIME_INTERVAL = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차를 정의
    private Toast toast;
    Button btnSell;
    EditText searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        btnSell = (Button)findViewById(R.id.sellBtn1);
        searchEdit = (EditText)findViewById(R.id.searchEdittext);


        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellMain.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {

            // 현재 시간을 backKeyPressedTime에 저장한다.
            backKeyPressedTime = System.currentTimeMillis();

            // 종료 안내문구를 노출한다.
            showMessage();
        }else{
            // 마지막 '뒤로'버튼 클릭시간이 이전 '뒤로'버튼 클릭시간과의 차이가 TIME_INTERVAL(2초)보다 작을때

            // Toast가 아직 노출중이라면 취소한다.
            toast.cancel();

            // 앱을 종료한다.
            this.finish();
        }

    }
    public void showMessage() {
        toast = Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT);
        toast.show();
    }
}