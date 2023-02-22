package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.repository.OrderingRepository;
import ua.masaltsev.autoservice2.service.OrderingService;

@Service
public class OrderingServiceImpl implements OrderingService {
    private static final int FAVOR_DISCOUNT_PERCENTAGE = 2;
    private static final int PRODUCT_DISCOUNT_PERCENTAGE = 1;
    private final OrderingRepository orderingRepository;

    public OrderingServiceImpl(OrderingRepository orderingRepository) {
        this.orderingRepository = orderingRepository;
    }

    @Override
    public Ordering save(Ordering ordering) {
        if (ordering.getReceivingTime() == null) {
            ordering.setReceivingTime(LocalDateTime.now());
        }
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

        long orderingCount = getOrderingList(id).size();

        BigDecimal productsDiscount = productsPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(orderingCount * PRODUCT_DISCOUNT_PERCENTAGE));
        BigDecimal favorDiscount = favorsPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(orderingCount * FAVOR_DISCOUNT_PERCENTAGE));

        BigDecimal price = productsPrice.subtract(productsDiscount)
                .add(favorsPrice).subtract(favorDiscount);

        ordering.setPrice(price);
        save(ordering);

        return price;
    }

    private Set<Ordering> getOrderingList(Long id) {
        return orderingRepository.getOrderingList(id);
    }
}
