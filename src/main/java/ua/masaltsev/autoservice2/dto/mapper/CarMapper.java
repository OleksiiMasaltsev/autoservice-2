package ua.masaltsev.autoservice2.dto.mapper;

import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.model.Car;

public class CarMapper implements RequestDtoMapper<CarRequestDto,
        Car>, ResponseDtoMapper<CarResponseDto, Car> {
    @Override
    public Car mapToModel(CarRequestDto dto) {
//        Car car = new Car();
//        car.setModel(dto.getModel());
//        car.setYear(dto.getYear());
//        car.setBrand(dto.getBrand());
//        car.setPlate(dto.getPlate());
//        car.setOwner(dto.getOwnerId());
        return null;
    }

    @Override
    public CarResponseDto mapToDto(Car car) {
        return null;
    }
}
