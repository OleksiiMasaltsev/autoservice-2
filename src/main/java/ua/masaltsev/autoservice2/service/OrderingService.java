package ua.masaltsev.autoservice2.service;

import java.math.BigDecimal;
import ua.masaltsev.autoservice2.model.Ordering;

public interface OrderingService {
    Ordering save(Ordering ordering);

    Ordering getById(Long id);

    void delete(Long id);

    BigDecimal calculatePrice(Long id);
}
