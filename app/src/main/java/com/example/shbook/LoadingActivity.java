package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        SharedPreferences pref = getSharedPreferences("LoginData",MODE_PRIVATE);
        Boolean logindata = pref.getBoolean("Login",false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (logindata) {
                    intent = new Intent(LoadingActivity.this, SearchMainActivity.class);
                }
                else {
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);

    }
}