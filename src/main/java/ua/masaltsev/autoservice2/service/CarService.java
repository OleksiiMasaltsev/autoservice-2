package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Car;

public interface CarService {
    Car save(Car car);

    Car getById(Long id);

    void deleteById(Long id);
}
