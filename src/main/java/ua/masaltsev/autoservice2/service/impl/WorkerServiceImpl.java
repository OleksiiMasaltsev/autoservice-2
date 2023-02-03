package ua.masaltsev.autoservice2.service.impl;

import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.repository.WorkerRepository;
import ua.masaltsev.autoservice2.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
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
}
