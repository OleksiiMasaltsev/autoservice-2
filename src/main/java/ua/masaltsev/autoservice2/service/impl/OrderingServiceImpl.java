package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.repository.OrderingRepository;
import ua.masaltsev.autoservice2.service.OrderingService;

@Service
public class OrderingServiceImpl implements OrderingService {
    private final OrderingRepository orderingRepository;

    public OrderingServiceImpl(OrderingRepository orderingRepository) {
        this.orderingRepository = orderingRepository;
    }

    @Override
    public Ordering save(Ordering ordering) {
        return orderingRepository.save(ordering);
    }

    @Override
    public Ordering getById(Long id) {
        return orderingRepository.getReferenceById(id);
    }

    @Override
    public void delete(Long id) {
        orderingRepository.deleteById(id);
    }

    @Override
    public BigDecimal calculatePrice(Long id) {
        Ordering ordering = getById(id);

        BigDecimal productsPrice = ordering.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal favorsPrice = ordering.getFavors().stream()
                .map(Favor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long orderingCount = ordering.getCar().getOwner().getOrderings().size();

        BigDecimal productsDiscount = productsPrice.divide(
                BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(orderingCount));
        BigDecimal favorDiscount = favorsPrice.divide(
                BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(orderingCount * 2));

        BigDecimal price = productsPrice.subtract(productsDiscount)
                .add(favorsPrice).subtract(favorDiscount);

        ordering.setPrice(price);
        save(ordering);

        return price;
    }
}
