package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Override
    @Query("from Worker w left join fetch w.orderings where w.id = ?1")
    Worker getReferenceById(Long id);
}
