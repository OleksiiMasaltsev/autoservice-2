package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Ordering;
import java.math.BigDecimal;

public interface OrderingService {
    Ordering save(Ordering ordering);

    Ordering getById(Long id);

    void delete(Long id);

    BigDecimal calculatePrice(Long id);
}
