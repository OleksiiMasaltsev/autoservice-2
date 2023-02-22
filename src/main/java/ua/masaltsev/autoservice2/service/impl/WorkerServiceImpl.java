package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.repository.FavorRepository;
import ua.masaltsev.autoservice2.repository.WorkerRepository;
import ua.masaltsev.autoservice2.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {
    private static final double SALARY_PERCENTAGE = 0.4;
    private final WorkerRepository workerRepository;
    private final FavorRepository favorRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository,
                             FavorRepository favorRepository) {
        this.workerRepository = workerRepository;
        this.favorRepository = favorRepository;
    }

    @Override
    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public Worker getById(Long id) {
        return workerRepository.getReferenceById(id);
    }

    @Override
    public void delete(Long id) {
        workerRepository.deleteById(id);
    }

    @Override
    public BigDecimal getSalary(Long id) {
        Worker worker = workerRepository.getReferenceById(id);
        return worker.getOrderings().stream()
                .flatMap(ordering -> ordering.getFavors().stream())
                .filter(favor -> favor.getStatus().equals(FavorStatus.UNPAID))
                .peek(favor -> favor.setStatus(FavorStatus.PAID))
                .peek(favorRepository::save)
                .map(Favor::getPrice)
                .reduce(BigDecimal.ZERO, (bd1, bd2) -> bd1.add(
                        bd2.multiply(BigDecimal.valueOf(SALARY_PERCENTAGE))));
    }
}
