package com.mysite.rentcar.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import com.mysite.rentcar.car.Car;
import com.mysite.rentcar.member.Member;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @Column(length = 30)
    private String resNumber;

    @Column
    private LocalDate resDate;

    @Column
    private LocalDate useBeginDate;

    @Column
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "resCarNumber")
    private Car resCarNumber;

    @ManyToOne
    @JoinColumn(name = "resUserId")
    private Member resUserId;

    @Column
    private Boolean isPaid = false;

    @Column
    private Integer paymentAmount;

    public Member getResUser() {
        return this.resUserId;
    }
}
