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
        Favor cheapFavor = new Favor();
        cheapFavor.setPrice(BigDecimal.valueOf(12));
        Favor expensiveFavor = new Favor();
        expensiveFavor.setPrice(BigDecimal.valueOf(18));
        return Set.of(cheapFavor, expensiveFavor);
    }

    private Set<Product> getProductList() {
        Product expensiveProduct = new Product();
        expensiveProduct.setPrice(BigDecimal.valueOf(15));
        Product cheapProduct = new Product();
        cheapProduct.setPrice(BigDecimal.valueOf(8));
        return Set.of(expensiveProduct, cheapProduct);
    }

    private List<Ordering> getOrderingList() {
        Ordering paidOrderingA = new Ordering();
        paidOrderingA.setStatus(OrderingStatus.PAID);
        Ordering paidOrderingB = new Ordering();
        paidOrderingB.setStatus(OrderingStatus.PAID);
        Ordering completedOrdering = new Ordering();
        completedOrdering.setStatus(OrderingStatus.COMPLETED_UNSUCCESSFULLY);
        Ordering paidOrderingC = new Ordering();
        paidOrderingC.setStatus(OrderingStatus.PAID);
        return List.of(paidOrderingA, paidOrderingB, completedOrdering, paidOrderingC);
    }
}
