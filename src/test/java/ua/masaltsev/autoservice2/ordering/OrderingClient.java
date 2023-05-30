package ua.masaltsev.autoservice2.ordering;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;

public class OrderingClient {

    private static final String ORDERINGS_ENDPOINT = "/orderings";

    public static ResponseEntity<OrderingResponseDto> save(TestRestTemplate template,
                                                           String rootUrl,
                                                           OrderingRequestDto requestDto) {
        HttpEntity<OrderingRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + ORDERINGS_ENDPOINT,
                HttpMethod.POST, request, OrderingResponseDto.class);
    }

    public static ResponseEntity<OrderingResponseDto> update(TestRestTemplate template,
                                                             String rootUrl,
                                                             OrderingRequestDto requestDto,
                                                             Long id) {
        HttpEntity<OrderingRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + ORDERINGS_ENDPOINT + "/" + id,
                HttpMethod.PUT, request, OrderingResponseDto.class);
    }
}
