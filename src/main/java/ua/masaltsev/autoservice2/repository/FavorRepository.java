package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Favor;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {
    @Override
    @Query("from Favor f " +
            "left join fetch f.ordering " +
            "left join fetch f.worker " +
            "where f.id = ?1")
    Favor getReferenceById(Long id);
}
