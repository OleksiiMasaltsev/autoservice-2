package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Car;

public interface CarService {
    Car save(Car car);
    Car getById(Long id);
    Car update(Car car);
    void delete(Long id);
}
