package com.example.shbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class SellPage extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스

    FirebaseStorage storage = FirebaseStorage.getInstance(); //스토리지 인스턴스 생성
    StorageReference storageRef = storage.getReference(); //스토리지참조
    Uri uri;


    //판매 세부사항 시트 중 DB에 받아올 데이터.
    private Button mBtnSell; // 판매완료 버튼

    private FirebaseDatabase database = FirebaseDatabase.getInstance(); //데이터베이스에 접근할 수 있는 진입점 클래스
    private DatabaseReference databaseReference = database.getReference(); //실시간 데이터베이스주소 저장

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
    String book_name;
    Elements contents;
    Document doc = null;
    Document doc1 = null;
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

            new Thread() {
                @Override
                public void run() {
                    try {
                        Document doc1 = (Document) Jsoup.connect("https://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=All&KeyWord=" + num + "&x=0&y=0/").get();
                        Elements bookName = doc1.select("a.c2b_b"); //제목
                        book_name = bookName.text().toString();
                        EditText BN = (EditText) findViewById(R.id.EditBookTitle);
                        BN.setText(book_name);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();


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
                            contents = doc.select("td.c2b_tablet3");

                            //크롤링된 doc 내용 중 td.c2b_table3(가격위치) 에있는 내용만 contents로 추출


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


        //isbn,책 상태, 금액, 이미지 ,특이사항
        mBtnSell = findViewById(R.id.nextBtn); //판매완료버튼
        final RadioGroup bookStatus = (RadioGroup)findViewById(R.id.RgroupBookStatus);
        final EditText bookPrice = findViewById(R.id.EditPrice);
        final EditText User_Text = findViewById(R.id.EditInfo);


        //버튼 누를시 데이터 베이스 연동 이벤트
        mBtnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //인식받은 isbn값 ==num;
                String strName = num;
               //라디오그룹 버튼 값 스트링형식으로 받아오기
                int status_id = bookStatus.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(status_id);
                String strStatus = rb.getText().toString();
                String strPrice = bookPrice.getText().toString();
                String strText = User_Text.getText().toString();


                //데이터베이스 처리 시작
                //버튼으로 처리한 값을 파베 RDB로 넘기는 함수
                databaseReference.child("Book_Sell").child("isbn").setValue(num);//책 isbn
                databaseReference.child("Book_Sell").child("status").setValue(strStatus);//책 상태
                databaseReference.child("Book_Sell").child("price").setValue(strPrice);//책 금액
                databaseReference.child("Book_Sell").child("text").setValue(strText);//사용자특이사항텍스트

                /* databaseReference.child("Book_Sell").child("img").setValue();//갤러리이미지
                 */

                Toast.makeText(SellPage.this, "도서등록에 성공하셨습니다", Toast.LENGTH_SHORT).show();

                 //판매페이지 값 intent 로 넘기기
                Intent intent2 = new Intent(getApplicationContext(), SellerDetailsActivity.class);
                intent2.putExtra("text",strText);
                startActivity(intent2);
                finish();

                Intent intent3 = new Intent(getApplicationContext(), SellerDetailsActivity.class);
                intent3.putExtra("price",strPrice);
                startActivity(intent3);
                finish();
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
            /*String filename = "book"+num+".jpg"; //파이어스토리지 작성중..
            Uri file = uri;
            Log.d("책이미지",String.valueOf(file));
            StorageReference riversRef = storageRef.child("book_img/"+filename);
            UploadTask uploadTask = riversRef.putFile(file);
*/

        }
    }
}
