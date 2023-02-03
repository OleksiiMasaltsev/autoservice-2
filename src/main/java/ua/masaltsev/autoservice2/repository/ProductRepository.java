package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
