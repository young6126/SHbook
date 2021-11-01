package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BookDetailsActivity extends AppCompatActivity {
    ConstraintLayout seller1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetails);
        seller1 = (ConstraintLayout)findViewById(R.id.seller1);
        seller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SellerDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
