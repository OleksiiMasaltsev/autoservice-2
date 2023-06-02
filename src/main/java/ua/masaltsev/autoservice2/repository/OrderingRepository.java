package ua.masaltsev.autoservice2.repository;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Ordering;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    @Query("from Ordering o left join fetch o.products where o.id = :id")
    Optional<Ordering> getFetchedById(Long id);

    @Query("select sum(pr.price) "
            + "from Ordering o "
            + "join o.products pr "
            + "where o.id = :id")
    BigDecimal getProductsPrice(Long id);

    @Query("select sum(f.price) "
            + "from Ordering o "
            + "join o.favors f "
            + "where o.id = :id")
    BigDecimal getFavorsPrice(Long id);

    @Query("select count(o) "
            + "from Owner o "
            + "join o.orderings ord "
            + "where ord.id = :id")
    Long getNumberOfPaidOrderings(Long id);
}
