package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Owner;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.repository.OrderingRepository;
import ua.masaltsev.autoservice2.service.OrderingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderingServiceImplTest {

    private OrderingService orderingService;

    private OrderingRepository orderingRepository;

    @BeforeEach
    void setUp() {
        orderingRepository = mock(OrderingRepository.class);
        orderingService = new OrderingServiceImpl(orderingRepository);
    }

    @Test
    void calculatePrice() {
        Owner owner = new Owner();
        owner.setOrderings(getTestOrderings());
        Car car = new Car();
        car.setOwner(owner);
        Ordering ordering = new Ordering();
        ordering.setId(15L);
        ordering.setCar(car);
        ordering.setProducts(getTestProducts());
        ordering.setFavors(getTestFavors());

        when(orderingRepository.getFetchedById(anyLong())).thenReturn(ordering);

        BigDecimal actual = orderingService.calculatePrice(15L);
        BigDecimal expected = BigDecimal.valueOf(50.51);

        assertEquals(expected, actual);
        verify(orderingRepository).getFetchedById(anyLong());
    }

    private Set<Favor> getTestFavors() {
        Favor favor1 = new Favor();
        favor1.setPrice(BigDecimal.valueOf(12));
        Favor favor2 = new Favor();
        favor2.setPrice(BigDecimal.valueOf(18));
        return Set.of(favor1, favor2);
    }

    private Set<Product> getTestProducts() {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(15));
        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(8));
        return Set.of(product1, product2);
    }

    private List<Ordering> getTestOrderings() {
        Ordering ordering1 = new Ordering();
        ordering1.setStatus(OrderingStatus.PAID);
        Ordering ordering2 = new Ordering();
        ordering2.setStatus(OrderingStatus.PAID);
        Ordering ordering3 = new Ordering();
        ordering3.setStatus(OrderingStatus.COMPLETED_UNSUCCESSFULLY);
        Ordering ordering4 = new Ordering();
        ordering4.setStatus(OrderingStatus.PAID);
        return List.of(ordering1, ordering2, ordering3, ordering4);
    }

    @Test
    void updateStatus() {
    }
}
