package com.mysite.rentcar.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> findCarByCarNumber(String carNumber) {
        return carRepository.findById(carNumber);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCarByCarNumber(String carNumber) {
        carRepository.deleteById(carNumber);
    }
}
