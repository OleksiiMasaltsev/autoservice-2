package ua.masaltsev.autoservice2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.repository.FavorRepository;
import ua.masaltsev.autoservice2.repository.WorkerRepository;
import ua.masaltsev.autoservice2.service.WorkerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkerServiceImplTest {
    private static final long ID = 15L;
    private static final double EXPECTED_VAL = 16.80;
    private WorkerService workerService;
    private WorkerRepository workerRepository;
    private FavorRepository favorRepository;

    @BeforeEach
    void setUp() {
        workerRepository = mock(WorkerRepository.class);
        favorRepository = mock(FavorRepository.class);
        workerService = new WorkerServiceImpl(workerRepository, favorRepository);
    }

    @Test
    void getSalary_twoSuccessfulOrdersThreeUnpaidFavors_correctSalary() {
        Set<Ordering> orderings = getOrderingList();
        Worker worker = new Worker();
        worker.setOrderings(orderings);
        when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));
        when(favorRepository.save(any(Favor.class))).thenReturn(any(Favor.class));

        BigDecimal expected = BigDecimal.valueOf(EXPECTED_VAL).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = workerService.getSalary(ID);

        assertEquals(expected, actual);
        verify(workerRepository).findById(anyLong());
        verify(favorRepository, times(3)).save(any(Favor.class));
    }

    @Test
    void getSalary_noSuccessfulOrdersThreeUnpaidFavors_zeroSalary() {
        Set<Ordering> orderings = getOrderingList();
        orderings
                .forEach(ordering -> ordering.setStatus(OrderingStatus.COMPLETED_UNSUCCESSFULLY));
        Worker worker = new Worker();
        worker.setOrderings(orderings);
        when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));
        when(favorRepository.save(any(Favor.class))).thenReturn(any(Favor.class));

        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = workerService.getSalary(ID);

        assertEquals(expected, actual);
        verify(workerRepository).findById(anyLong());
        verify(favorRepository, never()).save(any(Favor.class));
    }

    private Set<Favor> getFirstFavorList() {
        Favor cheapFavor = new Favor();
        cheapFavor.setStatus(FavorStatus.UNPAID);
        cheapFavor.setPrice(BigDecimal.valueOf(12));
        Favor expensiveFavor = new Favor();
        expensiveFavor.setStatus(FavorStatus.UNPAID);
        expensiveFavor.setPrice(BigDecimal.valueOf(18));
        return Set.of(cheapFavor, expensiveFavor);
    }

    private Set<Favor> getSecondFavorList() {
        Favor cheapFavor = new Favor();
        cheapFavor.setStatus(FavorStatus.UNPAID);
        cheapFavor.setPrice(BigDecimal.valueOf(12));
        Favor expensiveFavor = new Favor();
        expensiveFavor.setStatus(FavorStatus.PAID);
        expensiveFavor.setPrice(BigDecimal.valueOf(18));
        return Set.of(cheapFavor, expensiveFavor);
    }

    private Set<Ordering> getOrderingList() {
        Ordering successfulOrderingA = new Ordering();
        successfulOrderingA.setStatus(OrderingStatus.COMPLETED_SUCCESSFULLY);
        successfulOrderingA.setFavors(getFirstFavorList());

        Ordering successfulOrderingB = new Ordering();
        successfulOrderingB.setFavors(getSecondFavorList());
        successfulOrderingB.setStatus(OrderingStatus.COMPLETED_SUCCESSFULLY);

        Ordering unsuccessfulOrdering = new Ordering();
        unsuccessfulOrdering.setStatus(OrderingStatus.COMPLETED_UNSUCCESSFULLY);

        return Set.of(successfulOrderingA, successfulOrderingB, unsuccessfulOrdering);
    }
}
