package ua.masaltsev.autoservice2.dto.mapper;

import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.service.OwnerService;

@Component
public class CarMapper implements RequestDtoMapper<CarRequestDto,
        Car>, ResponseDtoMapper<CarResponseDto, Car> {
    private final OwnerService ownerService;

    public CarMapper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setYear(dto.getYear());
        car.setModel(dto.getModel());
        car.setBrand(dto.getBrand());
        car.setPlate(dto.getPlate());
        car.setOwner(ownerService.getById(dto.getOwnerId()));
        return car;
    }

    @Override
    public CarResponseDto mapToDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setYear(car.getYear());
        dto.setModel(car.getModel());
        dto.setBrand(car.getBrand());
        dto.setPlate(car.getPlate());
        dto.setOwnerId(car.getOwner().getId());
        return dto;
    }
}
