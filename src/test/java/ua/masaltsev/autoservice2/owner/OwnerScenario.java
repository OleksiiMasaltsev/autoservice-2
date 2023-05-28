package ua.masaltsev.autoservice2.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
import ua.masaltsev.autoservice2.ordering.OrderingClient;
import ua.masaltsev.autoservice2.product.ProductClient;
import ua.masaltsev.autoservice2.worker.WorkerClient;

public class OwnerScenario {
    public static void ownerCrud(TestRestTemplate template, String rootUrl) {
        OwnerResponseDto creationResponseDto = createOwner(template, rootUrl);
        getOwnerById(template, rootUrl, creationResponseDto);
        OwnerResponseDto updateResponseDto = updateOwner(template, rootUrl, creationResponseDto);
        deleteOwnerById(template, rootUrl, updateResponseDto);
    }

    private static void deleteOwnerById(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        ResponseEntity<OwnerResponseDto> successDeletionResponse =
                OwnerClient.deleteById(template, rootUrl, responseDto.getId());
        assertEquals(HttpStatus.NO_CONTENT, successDeletionResponse.getStatusCode());

        ResponseEntity<OwnerResponseDto> absentDeletionResponse =
                OwnerClient.deleteById(template, rootUrl, responseDto.getId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, absentDeletionResponse.getStatusCode());
    }

    private static OwnerResponseDto updateOwner(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        OwnerRequestDto updateRequestDto = new OwnerRequestDto();
        updateRequestDto.setName("Stepan");
        updateRequestDto.setCarIds(Collections.emptyList());
        updateRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> updateResponse =
                OwnerClient.update(template, rootUrl, updateRequestDto, responseDto.getId());

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(updateRequestDto.getName(), updateResponse.getBody().getName());
        return updateResponse.getBody();
    }

    private static void getOwnerById(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        ResponseEntity<OwnerResponseDto> response = OwnerClient.getById(template, rootUrl, responseDto.getId());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getId());
        assertEquals(responseDto.getName(), response.getBody().getName());
    }

    private static OwnerResponseDto createOwner(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto creationRequestDto = new OwnerRequestDto();
        creationRequestDto.setName("Oleksii");
        creationRequestDto.setCarIds(Collections.emptyList());
        creationRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> response =
                OwnerClient.save(template, rootUrl, creationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(creationRequestDto.getName(), response.getBody().getName());
        return response.getBody();
    }

    public static void getOrderings(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto ownerRequest = new OwnerRequestDto();
        ownerRequest.setName("Vasyl Boiko");
        ownerRequest.setCarIds(Collections.emptyList());
        ownerRequest.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerResponse =
                OwnerClient.save(template, rootUrl, ownerRequest);
        assertNotNull(ownerResponse.getBody());

        CarRequestDto carRequest = new CarRequestDto();
        carRequest.setOwnerId(ownerResponse.getBody().getId());
        carRequest.setBrand("Volvo");
        carRequest.setModel("VC30");
        carRequest.setYear(Short.valueOf("2019"));
        carRequest.setPlate("457898");
        ResponseEntity<CarResponseDto> carResponse =
                CarClient.save(template, rootUrl, carRequest);
        assertNotNull(carResponse.getBody());

        OrderingRequestDto orderingRequest = new OrderingRequestDto();
        orderingRequest.setCarId(carResponse.getBody().getId());
        orderingRequest.setFavorIds(Collections.emptyList());
        orderingRequest.setProductIds(Collections.emptyList());
        orderingRequest.setCompletionTime(LocalDateTime.now().plus(3L, ChronoUnit.HOURS));
        orderingRequest.setDescription("breaking system check");
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
        oilProductRequest.setPrice(BigDecimal.valueOf(17));
        ResponseEntity<ProductResponseDto> oilProductResponse =
                ProductClient.save(template, rootUrl, oilProductRequest);
        assertNotNull(oilProductResponse.getBody());

        ProductRequestDto breaksProductRequest  = new ProductRequestDto();
        breaksProductRequest.setName("breaking system");
        breaksProductRequest.setPrice(BigDecimal.valueOf(32));
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

        ResponseEntity<OwnerResponseDto[]> ownerOrderingsResponse =
                OwnerClient.getOrderings(template, rootUrl, updatedOwnerResponse.getBody().getId());
        assertNotNull(ownerOrderingsResponse.getBody());
        assertEquals(1, ownerOrderingsResponse.getBody().length);
    }
}
