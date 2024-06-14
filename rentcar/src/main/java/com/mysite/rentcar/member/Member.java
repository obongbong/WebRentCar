package com.mysite.rentcar.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @Column(length = 20)
    private String memId;

    @Column(length = 20)
    private String memPassword;

    @Column(length = 30)
    private String memName;

    @Column(length = 200)
    private String memAddress;

    @Column(length = 50)
    private String memPhoneNum;
    
 // Member 클래스 예시에서 Getter 메서드 추가
    public String getMemId() {
        return this.memId;
    }
    
    private String userId;

    // Getter method
    public String getUserId() {
        return userId;
    }

    // Setter method (if needed)
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
