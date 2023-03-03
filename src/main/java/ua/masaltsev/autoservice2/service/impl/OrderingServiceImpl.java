package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.repository.OrderingRepository;
import ua.masaltsev.autoservice2.service.OrderingService;

@Service
public class OrderingServiceImpl implements OrderingService {
    private static final float FAVOR_DISCOUNT_PERCENTAGE = 2.0f;
    private static final float PRODUCT_DISCOUNT_PERCENTAGE = 1.0f;
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

        BigDecimal productsTotalPrice = ordering.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal favorsTotalPrice = ordering.getFavors().stream()
                .map(Favor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int count = ordering.getCar().getOwner().getOrderings().size();

        BigDecimal productsDiscount = productsTotalPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(count * PRODUCT_DISCOUNT_PERCENTAGE));
        BigDecimal favorDiscount = favorsTotalPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(count * FAVOR_DISCOUNT_PERCENTAGE));

        BigDecimal price = productsTotalPrice.subtract(productsDiscount)
                .add(favorsTotalPrice).subtract(favorDiscount);

        ordering.setPrice(price);
        save(ordering);

        return price;
    }
}
