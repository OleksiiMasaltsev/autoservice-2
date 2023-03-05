package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderingServiceImplTest {
    private static final long ID = 15L;
    private static final double EXPECTED_VAL = 50.51;
    private static final int DIAG_PRICE = 20;
    private OrderingService orderingService;
    private OrderingRepository orderingRepository;

    @BeforeEach
    void setUp() {
        orderingRepository = mock(OrderingRepository.class);
        orderingService = new OrderingServiceImpl(orderingRepository);
    }

    @Test
    void calculatePrice_orderingWithProductsAndFavors_correctPrice() {
        Ordering ordering = getOrdering();
        when(orderingRepository.getFetchedById(anyLong())).thenReturn(ordering);
        BigDecimal actual = orderingService.calculatePrice(ID);
        BigDecimal expected = BigDecimal.valueOf(EXPECTED_VAL);
        assertEquals(expected, actual);
        verify(orderingRepository).getFetchedById(anyLong());
    }

    @Test
    void calculatePrice_orderingWithEmptyLists_takeAsDiag() {
        Ordering ordering = getOrdering();
        ordering.setProducts(Collections.emptySet());
        ordering.setFavors(Collections.emptySet());
        when(orderingRepository.getFetchedById(anyLong())).thenReturn(ordering);
        BigDecimal actual = orderingService.calculatePrice(ID);
        BigDecimal expected = BigDecimal.valueOf(DIAG_PRICE).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
        verify(orderingRepository).getFetchedById(anyLong());
    }

    @Test
    void updateStatus_completedStatus_completeDateChanges() {
        Ordering ordering = getOrdering();
        ordering.setStatus(OrderingStatus.COMPLETED_SUCCESSFULLY);
        when(orderingRepository.getReferenceById(anyLong())).thenReturn(ordering);
        when(orderingRepository.save(ordering)).thenReturn(ordering);
        assertNotNull(orderingService.updateStatus(
                OrderingStatus.COMPLETED_SUCCESSFULLY.toString(), ID).getCompletionTime());
        verify(orderingRepository).getReferenceById(anyLong());
        verify(orderingRepository).save(any(Ordering.class));
    }

    @Test
    void updateStatus_notCompletedStatus_completeDateDoNotChanges() {
        Ordering ordering = getOrdering();
        ordering.setStatus(OrderingStatus.RECEIVED);
        when(orderingRepository.getReferenceById(anyLong())).thenReturn(ordering);
        when(orderingRepository.save(ordering)).thenReturn(ordering);
        assertNull(orderingService.updateStatus(
                OrderingStatus.PROCEEDING.toString(), ID).getCompletionTime());
        verify(orderingRepository).getReferenceById(anyLong());
        verify(orderingRepository).save(any(Ordering.class));
    }

    private Ordering getOrdering() {
        Ordering ordering = new Ordering();
        Owner owner = new Owner();
        Car car = new Car();
        owner.setOrderings(getOrderingList());
        car.setOwner(owner);
        ordering.setId(ID);
        ordering.setCar(car);
        ordering.setProducts(getProductList());
        ordering.setFavors(getFavorList());
        return ordering;
    }

    private Set<Favor> getFavorList() {
        Favor favor1 = new Favor();
        favor1.setPrice(BigDecimal.valueOf(12));
        Favor favor2 = new Favor();
        favor2.setPrice(BigDecimal.valueOf(18));
        return Set.of(favor1, favor2);
    }

    private Set<Product> getProductList() {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(15));
        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(8));
        return Set.of(product1, product2);
    }

    private List<Ordering> getOrderingList() {
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
}
