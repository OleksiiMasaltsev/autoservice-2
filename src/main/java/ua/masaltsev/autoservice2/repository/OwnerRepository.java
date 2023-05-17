package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
//    @Query("from Owner o "
//            + "left join fetch o.orderings "
//            + "where o.id = :id")
//    Optional<Owner> getOwnerById(Long id);
}
