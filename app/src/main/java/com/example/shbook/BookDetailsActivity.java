package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BookDetailsActivity extends AppCompatActivity {

    Button mBtn_url;
    ConstraintLayout seller1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetails);
        seller1 = (ConstraintLayout)findViewById(R.id.seller1);
        mBtn_url = findViewById(R.id.sellerimg2);
        //사용자 중고
        seller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellerDetailsActivity.class);
                startActivity(intent);
            }
        }); //알라딘 중고
        mBtn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=282082743"));
                startActivity(urlintent);
            }
        });
    }
}
