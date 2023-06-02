package ua.masaltsev.autoservice2.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("from Worker w "
            + "left join fetch w.orderings ord "
            + "left join fetch ord.favors "
            + "where w.id = :id")
    public Optional<Worker> getFetchedById(Long id);
}
