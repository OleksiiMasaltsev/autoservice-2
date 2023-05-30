package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.CarMapper;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.service.CarService;

@Slf4j
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

    @Operation(summary = "save new car")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CarResponseDto save(@RequestBody CarRequestDto requestDto) {
        log.info("saving new car: {}", requestDto);
        return carMapper.mapToDto(carService.save(carMapper.mapToModel(requestDto)));
    }

    @Operation(summary = "get a car by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CarResponseDto getById(@PathVariable Long id) {
        log.info("getting a car with id: {}", id);
        return carMapper.mapToDto(carService.getById(id));
    }

    @Operation(summary = "update a car")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CarResponseDto update(@RequestBody CarRequestDto requestDto, @PathVariable Long id) {
        log.info("updating a car: {} by id: {}", requestDto, id);
        Car car = carMapper.mapToModel(requestDto);
        car.setId(id);
        return carMapper.mapToDto(carService.save(car));
    }

    @Operation(summary = "delete a car by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("deleting a car with id: {}", id);
        carService.deleteById(id);
    }
}
