package ua.masaltsev.autoservice2.dto.mapper;

import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.WorkerRequestDto;
import ua.masaltsev.autoservice2.dto.response.WorkerResponseDto;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.service.OrderingService;
import java.util.stream.Collectors;

@Component
public class WorkerMapper implements RequestDtoMapper<WorkerRequestDto, Worker>,
        ResponseDtoMapper<WorkerResponseDto, Worker> {
    private final OrderingService orderingService;

    public WorkerMapper(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @Override
    public Worker mapToModel(WorkerRequestDto dto) {
        Worker worker = new Worker();
        worker.setName(dto.getName());
        worker.setOrderings(dto.getOrderingIds().stream()
                .map(orderingService::getById)
                .collect(Collectors.toList()));
        return worker;
    }

    @Override
    public WorkerResponseDto mapToDto(Worker worker) {
        WorkerResponseDto dto = new WorkerResponseDto();
        dto.setId(worker.getId());
        dto.setName(worker.getName());
        dto.setOrderingIds(worker.getOrderings().stream()
                .map(Ordering::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
