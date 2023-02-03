package ua.masaltsev.autoservice2.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    public CarResponseDto save(@RequestBody CarRequestDto requestDto) {

    }
}
