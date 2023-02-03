package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Worker;

public interface WorkerService {
    Worker save(Worker worker);

    Worker getById(Long id);

    void delete(Long id);
}
