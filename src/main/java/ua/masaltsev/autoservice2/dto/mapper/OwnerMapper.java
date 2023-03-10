package ua.masaltsev.autoservice2.dto.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
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
                .collect(Collectors.toList()));

        owner.setOrderings(dto.getOrderingIds().stream()
                .map(orderingService::getById)
                .collect(Collectors.toList()));
        return owner;
    }

    @Override
    public OwnerResponseDto mapToDto(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        return dto;
    }
}
