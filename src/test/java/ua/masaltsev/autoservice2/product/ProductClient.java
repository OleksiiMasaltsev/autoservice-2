package ua.masaltsev.autoservice2.product;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.ProductRequestDto;
import ua.masaltsev.autoservice2.dto.response.ProductResponseDto;

public class ProductClient {
    public static ResponseEntity<ProductResponseDto> save(TestRestTemplate template,
                                                          String rootUrl,
                                                          ProductRequestDto requestDto) {
        HttpEntity<ProductRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/products", HttpMethod.POST, request, ProductResponseDto.class);
    }
}
