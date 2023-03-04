package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.repository.OrderingRepository;
import ua.masaltsev.autoservice2.service.OrderingService;

@Service
public class OrderingServiceImpl implements OrderingService {
    private static final float FAVOR_DISCOUNT_PERCENTAGE = 2.0f;
    private static final float PRODUCT_DISCOUNT_PERCENTAGE = 1.0f;
    private static final BigDecimal DIAG_PRICE = BigDecimal.valueOf(500);
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
        Ordering ordering = orderingRepository.getFetchedById(id);

        BigDecimal productsTotalPrice = ordering.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal favorsTotalPrice = ordering.getFavors().stream()
                .map(Favor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int count = ordering.getCar().getOwner().getOrderings().stream()
                .filter(ord -> ord.getStatus() == OrderingStatus.PAID)
                .toList()
                .size();

        BigDecimal productsDiscount = productsTotalPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(count * PRODUCT_DISCOUNT_PERCENTAGE));
        BigDecimal favorDiscount = favorsTotalPrice.divide(
                        BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(count * FAVOR_DISCOUNT_PERCENTAGE));

        BigDecimal price = productsTotalPrice.subtract(productsDiscount)
                .add(favorsTotalPrice).subtract(favorDiscount);

        if (price.compareTo(BigDecimal.ZERO) == 0) {
            price = DIAG_PRICE;
        }

        ordering.setPrice(price);
        save(ordering);

        return price;
    }

    @Override
    public Ordering updateStatus(String status, Long id) {
        Ordering ordering = getById(id);
        if (status.toUpperCase().equals(
                OrderingStatus.COMPLETED_SUCCESSFULLY.toString())
                 || status.toUpperCase().equals(
                         OrderingStatus.COMPLETED_UNSUCCESSFULLY.toString())) {
            ordering.setCompletionTime(LocalDateTime.now());
        }
        ordering.setStatus(OrderingStatus.valueOf(status.toUpperCase()));
        return save(ordering);
    }
}
