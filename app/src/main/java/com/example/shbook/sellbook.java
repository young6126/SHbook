package com.example.shbook;

/**
 * * 사용자 판매창 모듈
 * 사용자 고유 아이디를 통한 판매 데이터베이스 케이스
 */
public class sellbook {

    String book_isbn;
    String book_price;
    String book_status;
    String user_text;
    String user_book_img;

    public sellbook(){}

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public String getBook_status() {
        return book_status;
    }

    public void setBook_status(String book_status) {
        this.book_status = book_status;
    }

    public String getUser_text() {
        return user_text;
    }

    public void setUser_text(String user_text) {
        this.user_text = user_text;
    }

    public String getUser_book_img() {
        return user_book_img;
    }

    public void setUser_book_img(String user_book_img) {
        this.user_book_img = user_book_img;
    }

    public sellbook(String a, String b){
        this.book_price = getBook_price();
        this.user_text = getUser_text();
    }

}
