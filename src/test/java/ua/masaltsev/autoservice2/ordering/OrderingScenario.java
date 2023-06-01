package ua.masaltsev.autoservice2.ordering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
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
import ua.masaltsev.autoservice2.owner.OwnerClient;
import ua.masaltsev.autoservice2.product.ProductClient;
import ua.masaltsev.autoservice2.worker.WorkerClient;

public class OrderingScenario {
    public static void orderingCrud(TestRestTemplate template, String rootUrl) {
        OrderingResponseDto creationResponse = save(template, rootUrl);
    }

    private static OrderingResponseDto save(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ownerRequestDto.setCarIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerCreateResponse
                = OwnerClient.save(template, rootUrl, ownerRequestDto);
        assertNotNull(ownerCreateResponse.getBody());

        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setOwnerId(ownerCreateResponse.getBody().getId());
        carRequestDto.setYear(Short.valueOf("2017"));
        carRequestDto.setBrand("Skoda");
        carRequestDto.setModel("Octavia");
        carRequestDto.setPlate("124578");
        ResponseEntity<CarResponseDto> carCreateResponse =
                CarClient.save(template, rootUrl, carRequestDto);
        assertNotNull(carCreateResponse.getBody());

        OrderingRequestDto orderingRequestDto = new OrderingRequestDto();
        orderingRequestDto.setCarId(carCreateResponse.getBody().getId());
        orderingRequestDto.setStatus(OrderingStatus.RECEIVED.toString());
        orderingRequestDto.setDescription("change motor oil");
        LocalDateTime completionTime = LocalDateTime.now().plus(2L, ChronoUnit.HOURS);
        orderingRequestDto.setCompletionTime(completionTime);
        orderingRequestDto.setFavorIds(Collections.emptyList());
        orderingRequestDto.setProductIds(Collections.emptyList());
        ResponseEntity<OrderingResponseDto> orderingCreateResponse
                = OrderingClient.save(template, rootUrl, orderingRequestDto);

        assertEquals(HttpStatus.CREATED, orderingCreateResponse.getStatusCode());
        assertNotNull(orderingCreateResponse.getBody());
        assertEquals(orderingRequestDto.getStatus(), orderingCreateResponse.getBody().getStatus());
        assertEquals(orderingRequestDto.getDescription(), orderingCreateResponse.getBody().getDescription());
        assertEquals(orderingRequestDto.getCompletionTime(), orderingCreateResponse.getBody().getCompletionTime());
        return orderingCreateResponse.getBody();
    }

    public static void calculatePrice(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto ownerRequest = new OwnerRequestDto();
        ownerRequest.setName("Olena Zelenska");
        ownerRequest.setCarIds(Collections.emptyList());
        ownerRequest.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerResponse =
                OwnerClient.save(template, rootUrl, ownerRequest);
        assertNotNull(ownerResponse.getBody());

        CarRequestDto carRequest = new CarRequestDto();
        carRequest.setOwnerId(ownerResponse.getBody().getId());
        carRequest.setBrand("Skoda");
        carRequest.setModel("Fabia");
        carRequest.setYear(Short.valueOf("2018"));
        carRequest.setPlate("458899");
        ResponseEntity<CarResponseDto> carResponse =
                CarClient.save(template, rootUrl, carRequest);
        assertNotNull(carResponse.getBody());

        OrderingRequestDto orderingRequest = new OrderingRequestDto();
        orderingRequest.setCarId(carResponse.getBody().getId());
        orderingRequest.setFavorIds(Collections.emptyList());
        orderingRequest.setProductIds(Collections.emptyList());
        orderingRequest.setCompletionTime(LocalDateTime.now().plus(2L, ChronoUnit.HOURS));
        orderingRequest.setDescription("changing oil");
        orderingRequest.setStatus(OrderingStatus.RECEIVED.toString());
        ResponseEntity<OrderingResponseDto> orderingResponse =
                OrderingClient.save(template, rootUrl, orderingRequest);
        assertNotNull(orderingResponse.getBody());

        WorkerRequestDto workerRequest = new WorkerRequestDto();
        workerRequest.setOrderingIds(Set.of(
                orderingResponse.getBody().getId())
        );
        workerRequest.setName("Oleg");
        ResponseEntity<WorkerResponseDto> workerResponse = WorkerClient.save(template, rootUrl, workerRequest);
        assertNotNull(workerResponse.getBody());

        FavorRequestDto favorRequest  = new FavorRequestDto();
        favorRequest.setOrderingId(orderingResponse.getBody().getId());
        favorRequest.setWorkerId(workerResponse.getBody().getId());
        favorRequest.setPrice(BigDecimal.valueOf(5.45));
        favorRequest.setStatus(FavorStatus.UNPAID.toString());
        ResponseEntity<FavorResponseDto> favorResponse = FavorClient.save(template, rootUrl, favorRequest);
        assertNotNull(favorResponse.getBody());

        ProductRequestDto oilProductRequest  = new ProductRequestDto();
        oilProductRequest.setName("engine oil");
        oilProductRequest.setPrice(BigDecimal.valueOf(21));
        ResponseEntity<ProductResponseDto> oilProductResponse =
                ProductClient.save(template, rootUrl, oilProductRequest);
        assertNotNull(oilProductResponse.getBody());

        ProductRequestDto breaksProductRequest  = new ProductRequestDto();
        breaksProductRequest.setName("used materials");
        breaksProductRequest.setPrice(BigDecimal.valueOf(7));
        ResponseEntity<ProductResponseDto> breaksProductResponse =
                ProductClient.save(template, rootUrl, breaksProductRequest);
        assertNotNull(breaksProductResponse.getBody());

        orderingRequest.setFavorIds(List.of(favorResponse.getBody().getId()));
        orderingRequest.setProductIds(List.of(
                oilProductResponse.getBody().getId(),
                breaksProductResponse.getBody().getId()
        ));
        ResponseEntity<OrderingResponseDto> updatedOrderingResponse =
                OrderingClient.update(template, rootUrl, orderingRequest, orderingResponse.getBody().getId());
        assertNotNull(updatedOrderingResponse.getBody());

        ownerRequest.setCarIds(List.of(carResponse.getBody().getId()));
        ownerRequest.setOrderingIds(List.of(updatedOrderingResponse.getBody().getId()));
        ResponseEntity<OwnerResponseDto> updatedOwnerResponse =
                OwnerClient.update(template, rootUrl, ownerRequest, ownerResponse.getBody().getId());
        assertNotNull(updatedOwnerResponse.getBody());

        ResponseEntity<BigDecimal> calculatedPrice =
                OrderingClient.calculatePrice(template, rootUrl, updatedOrderingResponse.getBody().getId());
        assertNotNull(calculatedPrice.getBody());
        assertEquals(BigDecimal.valueOf(33.06), calculatedPrice.getBody());
    }
}
