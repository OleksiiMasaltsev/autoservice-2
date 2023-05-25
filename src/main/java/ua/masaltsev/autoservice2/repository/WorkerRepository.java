package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
//    @Override
//    @Query("from Worker w "
//            + "left join fetch w.orderings "
//            + "where w.id = :id")
//    Worker getReferenceById(Long id);
//
//    @Query("from Worker w "
//            + "left join fetch w.orderings ord "
//            + "left join fetch ord.favors "
//            + "left join fetch ord.products "
//            + "where w.id = :id")
//    Worker getFetchedById(Long id);
}
