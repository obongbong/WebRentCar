package com.mysite.rentcar.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/new")
    public String showCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "car_form";
    }

    @PostMapping
    public String submitCarForm(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "redirect:/cars/list";
    }

    @GetMapping("/redirect")
    public String redirectToNewCar() {
        return "redirect:/cars/new";
    }
    
    @GetMapping("/list")
    public String list(Model model) {
        List<Car> carList = carService.findAllCars();
        model.addAttribute("carList", carList);
        return "car_list";
    }

    @GetMapping("/edit/{carNumber}")
    public String showEditForm(@PathVariable("carNumber") String carNumber, Model model) {
        Optional<Car> carOptional = carService.findCarByCarNumber(carNumber);
        carOptional.ifPresent(car -> model.addAttribute("car", car));
        return "car_edit";
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "redirect:/cars/list";
    }

    @PostMapping("/delete/{carNumber}")
    public String deleteCar(@PathVariable("carNumber") String carNumber, RedirectAttributes redirectAttributes) {
        Optional<Car> carOptional = carService.findCarByCarNumber(carNumber);
        if (carOptional.isPresent()) {
            carService.deleteCarByCarNumber(carNumber);
            redirectAttributes.addFlashAttribute("successMessage", "차량을 삭제했습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "차량을 찾을 수 없습니다.");
        }
        return "redirect:/cars/list";
    }
    
    @GetMapping("/search")
    public String searchCarByCarNumber(@RequestParam(name = "carNumber", required = false) String carNumber, Model model) {
        if (carNumber == null || carNumber.isEmpty()) {
            List<Car> carList = carService.findAllCars();
            model.addAttribute("carList", carList);
        } else {
            Optional<Car> carOptional = carService.findCarByCarNumber(carNumber);
            if (carOptional.isPresent()) {
                model.addAttribute("carList", List.of(carOptional.get()));
            } else {
                model.addAttribute("errorMessage", "차량을 찾을 수 없습니다.");
            }
        }
        return "car_list";
    }

    @PostMapping("/toggleFavorite/{carNumber}")
    public String toggleFavorite(@PathVariable("carNumber") String carNumber, RedirectAttributes redirectAttributes) {
        Optional<Car> carOptional = carService.findCarByCarNumber(carNumber);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setFavorite(car.getFavorite() == null ? true : !car.getFavorite()); // null 체크 추가
            carService.saveCar(car);
            redirectAttributes.addFlashAttribute("successMessage", "즐겨찾기가 업데이트되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "차량을 찾을 수 없습니다.");
        }
        return "redirect:/cars/list";
    }
}
