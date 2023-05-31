package ua.masaltsev.autoservice2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("select o.orderings " +
            "from Owner o " +
            "where o.id = :id")
    List<Ordering> getOrderingsByOwnerId(Long id);
}
