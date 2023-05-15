package ua.masaltsev.autoservice2.service.impl;

import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.repository.CarRepository;
import ua.masaltsev.autoservice2.service.CarService;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get a car with id: " + id));
    }
}
