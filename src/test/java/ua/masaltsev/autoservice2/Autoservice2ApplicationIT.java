package ua.masaltsev.autoservice2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;

//@DirtiesContext
@SpringBootTest(
        classes = {Autoservice2Application.class}/*,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
class Autoservice2ApplicationIT {
    private static TestRestTemplate template;

    @BeforeAll
    static void beforeAll() {
        template = new TestRestTemplate();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void postOwner() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        String host = TestcontainersInitializer.getHost();
        Integer port = TestcontainersInitializer.getPort();
        String endpoint = "/owners";
        String url = String.format("http://%s:%d%s", host, port, endpoint);
        HttpEntity<OwnerRequestDto> request = new HttpEntity<>(ownerRequestDto);
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, request, String.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }
}
