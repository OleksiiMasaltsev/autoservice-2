package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Ordering;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    @Override
    @Query("from Ordering ord "
            + "left join fetch ord.car "
            + "left join fetch ord.products "
            + "where ord.id = ?1")
    Ordering getReferenceById(Long id);

    @Query("from Ordering ord "
            + "left join fetch ord.favors "
            + "left join fetch ord.products "
            + "left join fetch ord.car c "
            + "left join fetch c.owner o "
            + "left join fetch o.orderings "
            + "where ord.id = ?1")
    Ordering getFetchedById(Long id);
}
