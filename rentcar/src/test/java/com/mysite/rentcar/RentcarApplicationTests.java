package com.mysite.rentcar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.rentcar.car.Car;
import com.mysite.rentcar.car.CarRepository;
import com.mysite.rentcar.member.Member;
import com.mysite.rentcar.member.MemberRepository;
import com.mysite.rentcar.reservation.Reservation;
import com.mysite.rentcar.reservation.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RentcarApplicationTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testJpa() {
        // Car 데이터 생성 및 저장 (생략)

        // Member 데이터 생성 및 저장 (생략)

        // 예약 데이터 생성 및 저장
        Reservation res1 = new Reservation();
        res1.setResNumber("RES1001");
        res1.setResDate(LocalDate.now());
        res1.setUseBeginDate(LocalDate.of(2024, 6, 15));
        res1.setReturnDate(LocalDate.of(2024, 6, 20));
        res1.setResCarNumber(carRepository.findById("CAR1234").orElse(null));
        res1.setResUserId(memberRepository.findById("user1").orElse(null));
        reservationRepository.save(res1);

        Reservation res2 = new Reservation();
        res2.setResNumber("RES1002");
        res2.setResDate(LocalDate.now());
        res2.setUseBeginDate(LocalDate.of(2024, 6, 25));
        res2.setReturnDate(LocalDate.of(2024, 6, 30));
        res2.setResCarNumber(carRepository.findById("CAR5678").orElse(null));
        res2.setResUserId(memberRepository.findById("user1").orElse(null));
        reservationRepository.save(res2);

        // findAll() 메서드를 사용하여 모든 예약 데이터 조회
        List<Reservation> allReservations = reservationRepository.findAll();

        // 조회된 데이터 검증
        assertThat(allReservations).isNotNull();
        assertThat(allReservations.size()).isEqualTo(2);  // 추가된 예약 객체 수로 검증
    }
}
