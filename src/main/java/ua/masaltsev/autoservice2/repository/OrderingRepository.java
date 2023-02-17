package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Ordering;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    @Override
    @Query("from Ordering o left join fetch o.favors "
            + "left join fetch o.products "
            + "left join fetch o.car c "
            + "left join fetch c.owner owner "
            + "left join fetch owner.orderings "
            + "left join fetch owner.cars "
            + "where o.id = ?1")
    Ordering getReferenceById(Long id);
}
