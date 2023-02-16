package ua.masaltsev.autoservice2.dto.mapper;

import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.service.OrderingService;
import ua.masaltsev.autoservice2.service.WorkerService;

@Component
public class FavorMapper implements RequestDtoMapper<FavorRequestDto, Favor>,
        ResponseDtoMapper<FavorResponseDto, Favor> {
    private final OrderingService orderingService;
    private final WorkerService workerService;

    public FavorMapper(OrderingService orderingService, WorkerService workerService) {
        this.orderingService = orderingService;
        this.workerService = workerService;
    }

    @Override
    public Favor mapToModel(FavorRequestDto dto) {
        Favor favor = new Favor();
        favor.setPrice(dto.getPrice());
        favor.setDescription(dto.getDescription());
        favor.setWorker(workerService.getById(dto.getWorkerId()));
        favor.setOrdering(orderingService.getById(dto.getOrderingId()));
        favor.setStatus(FavorStatus.valueOf(dto.getStatus().toUpperCase()));
        return favor;
    }

    @Override
    public FavorResponseDto mapToDto(Favor favor) {
        FavorResponseDto dto = new FavorResponseDto();
        dto.setId(favor.getId());
        dto.setPrice(favor.getPrice());
        dto.setDescription(favor.getDescription());
        dto.setWorkerId(favor.getWorker().getId());
        dto.setOrderingId(favor.getOrdering().getId());
        dto.setStatus(favor.getStatus().toString());
        return dto;
    }
}
