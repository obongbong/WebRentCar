package com.mysite.rentcar.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservationForm")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation_form";
    }

    @PostMapping("/reservations")
    public String submitReservationForm(@ModelAttribute Reservation reservation) {
        reservationService.saveReservation(reservation);
        return "redirect:/reservations/list";
    }

    @GetMapping("/list")
    public String listReservations(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);
        return "reservation_list";
    }

    @GetMapping("/edit/{resNumber}")
    public String showEditForm(@PathVariable("resNumber") String resNumber, Model model) {
        Optional<Reservation> reservationOptional = reservationService.findReservationByResNumber(resNumber);
        reservationOptional.ifPresent(reservation -> model.addAttribute("reservation", reservation));
        return "reservation_edit";
    }

    @PostMapping("/update")
    public String updateReservation(@ModelAttribute Reservation reservation) {
        reservationService.saveReservation(reservation);
        return "redirect:/reservations/list";
    }

    @PostMapping("/delete/{resNumber}")
    public String deleteReservation(@PathVariable("resNumber") String resNumber) {
        reservationService.deleteReservationByResNumber(resNumber);
        return "redirect:/reservations/list";
    }

    @GetMapping("/search")
    public String searchReservationByResNumber(@RequestParam(name = "resNumber", required = false) String resNumber, Model model) {
        if (resNumber == null || resNumber.isEmpty()) {
            List<Reservation> reservationList = reservationService.findAllReservations();
            model.addAttribute("reservationList", reservationList);
        } else {
            Optional<Reservation> reservationOptional = reservationService.findReservationByResNumber(resNumber);
            if (reservationOptional.isPresent()) {
                model.addAttribute("reservationList", List.of(reservationOptional.get()));
            } else {
                model.addAttribute("errorMessage", "Reservation not found.");
            }
        }
        return "reservation_list";
    }

    @GetMapping("/new")
    public String redirectToNewReservationForm() {
        return "redirect:/reservations/reservationForm";
    }

    @PostMapping("/pay/{resNumber}")
    public String payReservation(@PathVariable("resNumber") String resNumber, Model model) {
        Optional<Reservation> reservationOptional = reservationService.findReservationByResNumber(resNumber);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            long days = ChronoUnit.DAYS.between(reservation.getUseBeginDate(), reservation.getReturnDate()) + 1;
            int paymentAmount = (int) (days * 40000);
            reservation.setPaymentAmount(paymentAmount);
            reservation.setIsPaid(true);
            reservationService.saveReservation(reservation);
            model.addAttribute("paymentMessage", "결제 완료되었습니다. 금액: " + paymentAmount + "원");
        } else {
            model.addAttribute("paymentMessage", "결제 실패: 예약을 찾을 수 없습니다.");
        }
        return "redirect:/reservations/list";
    }
}
