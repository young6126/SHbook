package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchMainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;    // '뒤로' 버튼을 클릭했을 때의 시간
    private long TIME_INTERVAL = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차를 정의
    private Toast toast;
    Button btnSell;
    EditText searchEdit;
    TextView profileText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        SharedPreferences pref = getSharedPreferences("LoginData",MODE_PRIVATE);
        String loginData = pref.getString("ID","사용자") + "님, \n안녕하세요.";
        btnSell = (Button)findViewById(R.id.sellBtn1);
        searchEdit = (EditText)findViewById(R.id.searchEdittext);
        profileText1 = (TextView)findViewById(R.id.profileText1);
        profileText1.setText(loginData);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellMain.class);
                startActivity(intent);
                finish();
            }
        });
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String text = searchEdit.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),SearchResultActivity.class);
                    intent.putExtra("Searchhint",text);
                    startActivity(intent);
                    return true;
                }
                return false;
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