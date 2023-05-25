package ua.masaltsev.autoservice2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ua.masaltsev.autoservice2.client.CarClient;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.initializer.TestcontainersInitializer;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.client.OrderingClient;
import ua.masaltsev.autoservice2.client.OwnerClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
class Autoservice2ApplicationIT {
    private static TestRestTemplate template;
    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        template = new TestRestTemplate();
    }

    @Test
    void saveOwner_ok() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        ownerRequestDto.setCarIds(Collections.emptyList());
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> response =
                OwnerClient.save(template, getRootUrl(), ownerRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ownerRequestDto.getName(), response.getBody().getName());
    }

    @Test
    void updateOwner_ok() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        ownerRequestDto.setCarIds(Collections.emptyList());
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> creationResponse =
                OwnerClient.save(template, getRootUrl(), ownerRequestDto);

        assertNotNull(creationResponse.getBody());
        Long id = creationResponse.getBody().getId();
        ownerRequestDto.setName("Stepan");
        ResponseEntity<OwnerResponseDto> updateResponse =
                OwnerClient.update(template, getRootUrl(), ownerRequestDto, id);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(ownerRequestDto.getName(), updateResponse.getBody().getName());
    }

    @Test
    void saveOrdering_ok() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ownerRequestDto.setCarIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerCreateResponse
                = OwnerClient.save(template, getRootUrl(), ownerRequestDto);
        assertNotNull(ownerCreateResponse.getBody());

        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setOwnerId(ownerCreateResponse.getBody().getId());
        carRequestDto.setYear(Short.valueOf("2017"));
        carRequestDto.setBrand("Skoda");
        carRequestDto.setModel("Octavia");
        carRequestDto.setPlate("124578");
        ResponseEntity<CarResponseDto> carCreateResponse =
                CarClient.save(template, getRootUrl(), carRequestDto);
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
                = OrderingClient.save(template, getRootUrl(), orderingRequestDto);

        assertEquals(HttpStatus.CREATED, orderingCreateResponse.getStatusCode());
        assertNotNull(orderingCreateResponse.getBody());
        assertEquals(orderingRequestDto.getStatus(), orderingCreateResponse.getBody().getStatus());
        assertEquals(orderingRequestDto.getDescription(), orderingCreateResponse.getBody().getDescription());
        assertEquals(orderingRequestDto.getCompletionTime(), orderingCreateResponse.getBody().getCompletionTime());
    }

    private String getRootUrl() {
        return String.format("http://localhost:%d", port);
    }
}
