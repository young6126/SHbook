package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SellerDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellerdetails);

        Intent intent = getIntent();
        String user_book_text = intent.getStringExtra("text");

        TextView UBT = (TextView) findViewById(R.id.sellText);
        UBT.setText("특이사항:"+user_book_text);

        Intent intent1 = getIntent();
        String user_book_price = intent1.getStringExtra("price");

        TextView UBP = (TextView) findViewById(R.id.sellPrice);
        UBP.setText("판매가 "+user_book_price+"원");

        Intent intent2 = getIntent();
        String user_book_condition = intent.getStringExtra("condition");

        TextView UBC = (TextView) findViewById(R.id.condition);
        UBC.setText("상태 "+user_book_condition);


    }
}

