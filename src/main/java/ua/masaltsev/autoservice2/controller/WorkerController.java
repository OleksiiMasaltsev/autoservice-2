package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.OrderingMapper;
import ua.masaltsev.autoservice2.dto.mapper.WorkerMapper;
import ua.masaltsev.autoservice2.dto.request.WorkerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.dto.response.WorkerResponseDto;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.service.WorkerService;

@RestController
@RequestMapping("/workers")
@Tag(name = "Worker controller")
public class WorkerController {
    private final WorkerService workerService;
    private final WorkerMapper workerMapper;
    private final OrderingMapper orderingMapper;

    public WorkerController(WorkerService workerService,
                            WorkerMapper workerMapper,
                            OrderingMapper orderingMapper) {
        this.workerService = workerService;
        this.workerMapper = workerMapper;
        this.orderingMapper = orderingMapper;
    }

    @PostMapping
    @Operation(summary = "save new worker")
    public WorkerResponseDto save(@RequestBody WorkerRequestDto requestDto) {
        return workerMapper.mapToDto(workerService.save(workerMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a worker")
    public WorkerResponseDto update(@RequestBody WorkerRequestDto requestDto,
                                    @PathVariable Long id) {
        Worker worker = workerMapper.mapToModel(requestDto);
        worker.setId(id);
        return workerMapper.mapToDto(workerService.save(worker));
    }

    @GetMapping("/{id}/orderings")
    @Operation(summary = "get a list of worker's orderings")
    public List<OrderingResponseDto> getOrderings(@PathVariable Long id) {
        return workerService.getById(id).getOrderings().stream()
                .map(orderingMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}/salary")
    @Operation(summary = "calculate and get worker's salary")
    public BigDecimal calculateAndGetSalary(@PathVariable Long id) {
        return workerService.getSalary(id);
    }
}
