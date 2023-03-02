package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Ordering;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {

    @Query("from Ordering ord "
            + "left join fetch ord.car c "
            + "left join fetch c.owner o "
            + "left join fetch o.orderings "
            + "left join fetch ord.products "
            + "left join fetch ord.favors "
            + "where ord.id = ?1")
    Ordering getFetchedOrdering(Long id);
}
