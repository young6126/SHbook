package com.example.shbook;

/**
 * * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String idToken; //Firebase Uid 고유토큰정보
    private String emailId; //이메일 아이디
    private String password; //비밀번호
    private String age; //나이

    public UserAccount() { }  //Firebase 모델을 사용할때는 빈생성자가 무조건있어야함

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
}
