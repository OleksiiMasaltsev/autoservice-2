package ua.masaltsev.autoservice2.service;

import java.math.BigDecimal;
import ua.masaltsev.autoservice2.model.Worker;

public interface WorkerService {
    Worker save(Worker worker);

    Worker getById(Long id);

    Worker getFetchedById(Long id);

    void delete(Long id);

    BigDecimal getSalary(Long id);
}
