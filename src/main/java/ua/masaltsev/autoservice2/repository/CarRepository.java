package ua.masaltsev.autoservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.masaltsev.autoservice2.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
