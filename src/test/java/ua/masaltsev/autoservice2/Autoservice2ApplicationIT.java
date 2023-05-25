package ua.masaltsev.autoservice2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.initializer.TestcontainersInitializer;
import ua.masaltsev.autoservice2.owner.OwnerClient;
import ua.masaltsev.autoservice2.owner.OwnerRequestHolder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
class Autoservice2ApplicationIT {
    private static TestRestTemplate template;
    @LocalServerPort
    private Integer localServerPort;

    @BeforeAll
    static void beforeAll() {
        template = new TestRestTemplate();
    }

    @Test
    void saveOwner_ok() {
        OwnerRequestDto requestDto = OwnerRequestHolder.getRequest();
        ResponseEntity<OwnerResponseDto> response = OwnerClient.save(template, localServerPort, requestDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(requestDto.getName(), response.getBody().getName());
    }

    @Test
    void updateOwner_ok() {
        OwnerRequestDto requestDto = OwnerRequestHolder.getRequest();
        ResponseEntity<OwnerResponseDto> creationResponse = OwnerClient.save(template, localServerPort, requestDto);
        assertNotNull(creationResponse.getBody());
        Long id = creationResponse.getBody().getId();
        requestDto.setName("Stepan");
        ResponseEntity<OwnerResponseDto> updateResponse = OwnerClient.update(template, localServerPort, requestDto, id);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(requestDto.getName(), updateResponse.getBody().getName());
    }
}
