package com.example.shbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerDetailsActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListenter;
    private TextView textView;
    private String UBT, UBP, UBC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellerdetails);

    }
}

/*
        mDatabaseReference.child("Book_Sell").child("text").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String value = datasnapshot.getValue(String.class);
                UBT = value.toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TextView User_Book_Text = (TextView) findViewById(R.id.sellText);
        User_Book_Text.setText("특이사항:"+UBT);

    }
*/


        /*
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

*/


