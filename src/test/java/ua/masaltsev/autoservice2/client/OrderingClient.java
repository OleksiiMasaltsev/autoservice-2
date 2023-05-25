package ua.masaltsev.autoservice2.client;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;

public class OrderingClient {
    public static ResponseEntity<OrderingResponseDto> save(TestRestTemplate template,
                                                           String rootUrl,
                                                           OrderingRequestDto requestDto) {
        HttpEntity<OrderingRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/orderings", HttpMethod.POST, request, OrderingResponseDto.class);
    }
}
