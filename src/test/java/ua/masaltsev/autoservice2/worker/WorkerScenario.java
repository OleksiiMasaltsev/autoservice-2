package ua.masaltsev.autoservice2.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.car.CarClient;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.request.ProductRequestDto;
import ua.masaltsev.autoservice2.dto.request.WorkerRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.dto.response.ProductResponseDto;
import ua.masaltsev.autoservice2.dto.response.WorkerResponseDto;
import ua.masaltsev.autoservice2.favor.FavorClient;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.ordering.OrderingClient;
import ua.masaltsev.autoservice2.owner.OwnerClient;
import ua.masaltsev.autoservice2.product.ProductClient;

public class WorkerScenario {
    public static void calculateWorkersSalary(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Hryhorii Yuschenko");
        ownerRequestDto.setCarIds(Collections.emptyList());
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerResponseEntity =
                OwnerClient.save(template, rootUrl, ownerRequestDto);
        assertNotNull(ownerResponseEntity.getBody());

        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setOwnerId(ownerResponseEntity.getBody().getId());
        carRequestDto.setBrand("Hyundai");
        carRequestDto.setModel("H1");
        carRequestDto.setPlate("BE5487CH");
        carRequestDto.setYear(Short.valueOf("2009"));
        ResponseEntity<CarResponseDto> carResponseEntity =
                CarClient.save(template, rootUrl, carRequestDto);
        assertNotNull(carResponseEntity.getBody());

        OrderingRequestDto orderingRequestDto = new OrderingRequestDto();
        orderingRequestDto.setCarId(carResponseEntity.getBody().getId());
        orderingRequestDto.setStatus(OrderingStatus.COMPLETED_SUCCESSFULLY.toString());
        orderingRequestDto.setFavorIds(Collections.emptyList());
        orderingRequestDto.setProductIds(Collections.emptyList());
        orderingRequestDto.setDescription("head light check");
        orderingRequestDto.setCompletionTime(LocalDateTime.now().plus(1L, ChronoUnit.HOURS));
        ResponseEntity<OrderingResponseDto> orderingResponseEntity =
                OrderingClient.save(template, rootUrl, orderingRequestDto);
        assertNotNull(orderingResponseEntity.getBody());

        WorkerRequestDto workerRequestDto = new WorkerRequestDto();
        workerRequestDto.setName("Mark Honchar");
        workerRequestDto.setOrderingIds(Set.of(
                orderingResponseEntity.getBody().getId()));
        ResponseEntity<WorkerResponseDto> workerResponseEntity =
                WorkerClient.save(template, rootUrl, workerRequestDto);
        assertNotNull(workerResponseEntity.getBody());

        FavorRequestDto favorRequestDto = new FavorRequestDto();
        favorRequestDto.setOrderingId(orderingResponseEntity.getBody().getId());
        favorRequestDto.setWorkerId(workerResponseEntity.getBody().getId());
        favorRequestDto.setStatus(FavorStatus.UNPAID.toString());
        favorRequestDto.setPrice(BigDecimal.valueOf(10.00));
        ResponseEntity<FavorResponseDto> favorResponseEntity =
                FavorClient.save(template, rootUrl, favorRequestDto);
        assertNotNull(favorResponseEntity.getBody());

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("head light bulb");
        productRequestDto.setPrice(BigDecimal.valueOf(6.33));
        ResponseEntity<ProductResponseDto> productResponseEntity =
                ProductClient.save(template, rootUrl, productRequestDto);
        assertNotNull(productResponseEntity.getBody());

        orderingRequestDto.setFavorIds(List.of(
                favorResponseEntity.getBody().getId()
        ));
        orderingRequestDto.setProductIds(List.of(
                productResponseEntity.getBody().getId()
        ));
        orderingResponseEntity = OrderingClient.update(template, rootUrl,
                orderingRequestDto, orderingResponseEntity.getBody().getId());
        assertNotNull(orderingResponseEntity.getBody());

        ResponseEntity<BigDecimal> salaryResponseEntity =
                WorkerClient.getSalary(template, rootUrl, workerResponseEntity.getBody().getId());
        assertNotNull(salaryResponseEntity.getBody());
        assertEquals(BigDecimal.valueOf(4.00).setScale(2, RoundingMode.HALF_UP),
                salaryResponseEntity.getBody());
    }
}
