package com.example.shbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecommendBook1 extends AppCompatActivity {
    Button btnSeller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommen_book1);

        btnSeller = (Button)findViewById(R.id.sellerimg1);
        btnSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendBookSell.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
