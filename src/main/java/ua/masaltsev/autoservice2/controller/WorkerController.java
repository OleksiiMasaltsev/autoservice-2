package ua.masaltsev.autoservice2.controller;

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
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.service.OrderingService;
import ua.masaltsev.autoservice2.service.WorkerService;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerService workerService;
    private final OrderingService orderingService;
    private final WorkerMapper workerMapper;
    private final OrderingMapper orderingMapper;

    public WorkerController(WorkerService workerService,
                            OrderingService orderingService,
                            WorkerMapper workerMapper,
                            OrderingMapper orderingMapper) {
        this.workerService = workerService;
        this.orderingService = orderingService;
        this.workerMapper = workerMapper;
        this.orderingMapper = orderingMapper;
    }

    @PostMapping
    public WorkerResponseDto save(@RequestBody WorkerRequestDto requestDto) {
        return workerMapper.mapToDto(workerService.save(workerMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    public WorkerResponseDto update(@RequestBody WorkerRequestDto requestDto,
                                    @PathVariable Long id) {
        Worker worker = workerMapper.mapToModel(requestDto);
        worker.setId(id);
        return workerMapper.mapToDto(workerService.save(worker));
    }

    @GetMapping("/orderings/{id}")
    public List<OrderingResponseDto> getOrderings(@PathVariable Long id) {
        return workerService.getById(id).getOrderings().stream()
                .map(orderingMapper::mapToDto)
                .toList();
    }

//    @GetMapping("/{id}")
//    public BigDecimal calculateAndPay(@PathVariable Long id) {
//        List<Ordering> orderings = workerService.getById(id).getOrderings();
//        List<Favor> filteredFavors = orderings.stream()
//                .flatMap(ordering -> ordering.getFavors().stream())
//                .filter(favor -> favor.getWorker().getId().equals(id))
//                .toList();
//        filteredFavors
//                .forEach(favor -> favor.setStatus(FavorStatus.PAID));
//
//    }
}
