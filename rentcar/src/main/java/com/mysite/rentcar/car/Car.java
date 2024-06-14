package com.mysite.rentcar.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Car {
    @Id
    @Column(length = 30)
    private String carNumber;

    @Column(length = 50)
    private String carName;

    @Column(length = 30)
    private String carColor;

    @Column
    private Integer carSize;

    @Column(length = 50)
    private String carMaker;

    @Column
    private Boolean favorite = false;  // 기본값을 false로 설정
}
