package ua.masaltsev.autoservice2.dto.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Owner;
import ua.masaltsev.autoservice2.service.CarService;
import ua.masaltsev.autoservice2.service.OrderingService;

@Component
public class OwnerMapper implements RequestDtoMapper<OwnerRequestDto, Owner>,
        ResponseDtoMapper<OwnerResponseDto, Owner> {
    private final CarService carService;
    private final OrderingService orderingService;

    public OwnerMapper(CarService carService, OrderingService orderingService) {
        this.carService = carService;
        this.orderingService = orderingService;
    }

    @Override
    public Owner mapToModel(OwnerRequestDto dto) {
        Owner owner = new Owner();
        owner.setName(dto.getName());

        owner.setCars(dto.getCarIds().stream()
                .map(carService::getById)
                .collect(Collectors.toSet()));

        owner.setOrderings(dto.getOrderingIds().stream()
                .map(orderingService::getById)
                .collect(Collectors.toSet()));
        return owner;
    }

    @Override
    public OwnerResponseDto mapToDto(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());

        dto.setCarIds(owner.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toSet()));

        dto.setOrderingIds(owner.getOrderings().stream()
                .map(Ordering::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}
