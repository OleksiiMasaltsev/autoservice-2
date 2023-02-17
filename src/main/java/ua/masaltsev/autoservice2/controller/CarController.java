package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.CarMapper;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.service.CarService;

@RestController
@RequestMapping("/cars")
@Tag(name = "Car controller")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @PostMapping
    @Operation(summary = "save new car")
    public CarResponseDto save(@RequestBody CarRequestDto requestDto) {
        return carMapper.mapToDto(carService.save(carMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a car")
    public CarResponseDto update(@RequestBody CarRequestDto requestDto, @PathVariable Long id) {
        Car car = carMapper.mapToModel(requestDto);
        car.setId(id);
        return carMapper.mapToDto(carService.save(car));
    }
}
