package com.example.shbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class SellPage extends AppCompatActivity {

    View priceView;
    View statusView;

    Button priceCompare;
    Button bookStatus;

    ImageView bookImage;
    ImageView bookImage2;
    ImageView bookImage3;
    Button imageAddBtn;
    int cnt = 0;

    private static final int REQUEST_CODE = 0;

    RadioGroup radioGroup;
    RadioButton veryGood, good, normal;
    TextView tvStatus;

    TextView textView;
    Elements contents;
    Document doc = null;
    String price;
    String num = "9788932917245"; //어린왕자예시 num은 인식변수로 가져올 것
    String status[] = {"정가", "최상", "상", "중"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_page);

        priceCompare = (Button)findViewById(R.id.BtnPriceCheck);

        Intent intent = getIntent();
        String requsetCode = intent.getStringExtra("REQUEST_CODE");

        num = requsetCode;  //인식한 isbn 코드로 변경

        bookStatus = (Button)findViewById(R.id.BtnBookStatusGuide);
        priceCompare = (Button)findViewById(R.id.BtnPriceCheck);
        imageAddBtn = (Button)findViewById(R.id.BtnImageAttach);
        bookImage = (ImageView)findViewById(R.id.ImgAttach1);
        bookImage2 = (ImageView)findViewById(R.id.ImgAttach2);
        bookImage3 = (ImageView)findViewById(R.id.ImgAttach3);

        //도서 상태 라디오 버튼
        radioGroup = (RadioGroup)findViewById(R.id.RgroupBookStatus);
        veryGood = (RadioButton)findViewById(R.id.veryGood);
        good = (RadioButton)findViewById(R.id.good);
        normal = (RadioButton)findViewById(R.id.normal);
        tvStatus = (TextView)findViewById(R.id.TextBookStatus);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.veryGood){
                    tvStatus.setText("책상태 : 최상 선택됨");
                }
                else if(i == R.id.good){
                    tvStatus.setText("책상태 : 상 선택됨");
                }
                else if(i == R.id.normal){
                    tvStatus.setText("책상태 : 중 선택됨");
                }
            }
        });

        //이미지 첨부
        imageAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt = 0;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        bookImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt = 1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        bookImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt = 2;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //책 상태 측정 가이드 다이얼 로그 창
        bookStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusView = (View)View.inflate(SellPage.this, R.layout.book_status, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(SellPage.this);

                dlg.setTitle("중고 도서 상태 측정 가이드");
                dlg.setView(statusView);
                dlg.setPositiveButton("닫기", null);

                dlg.show();
            }
        });

        //가격비교 다이얼로그 창
        priceCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceView = (View)View.inflate(SellPage.this, R.layout.price_comparison, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(SellPage.this);

                dlg.setView(priceView);
                dlg.setPositiveButton("닫기", null);

                textView = (TextView) priceView.findViewById(R.id.textBox);

                new AsyncTask() {//AsyncTask객체 생성
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            doc = (Document) Jsoup.connect("https://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=All&KeyWord="+num+"&x=0&y=0/").get();
                            //jsoup 링크연결 url에 있는 모든 내용을 .get()으로 doc 에 수집
                            contents = doc.select("td.c2b_tablet3"); //크롤링된 doc 내용 중 td.c2b_table3(가격위치) 에있는 내용만 contents로 추출


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        price = ""; //contents의 내용이 배열로 담겨있을 수 있기에 배열 프린트 생성
                        int cnt = 0; //if문 세기위한 변수
                        for(Element element: contents) {
                            price += status[cnt]+". "+element.text() + "\n";
                            cnt++;
                            if(cnt == 10)
                                break;
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        Log.i("TAG",""+price);
                        textView.setText(price);
                    }
                }.execute();

                dlg.show();
            }
        });

    }
    //이미지 첨부
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img1 = BitmapFactory.decodeStream(in);
                    in.close();

                    if(cnt == 0){
                        bookImage.setImageBitmap(img1);
                        cnt++;
                    }
                    else if(cnt == 1){
                        bookImage2.setImageBitmap(img1);
                        cnt++;
                    }
                    else if(cnt == 2){
                        bookImage3.setImageBitmap(img1);
                        cnt++;
                    }
                    else{
                        Toast.makeText(this, "최대  3장 첨부 가능합니다. 바꾸시려면 이미지를 클릭하세요", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
