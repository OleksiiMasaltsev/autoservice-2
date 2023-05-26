package ua.masaltsev.autoservice2.client;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;

public class OwnerClient {
    public static ResponseEntity<OwnerResponseDto> save(TestRestTemplate template,
                                                        String rootUrl,
                                                        OwnerRequestDto requestDto) {
        HttpEntity<OwnerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/owners", HttpMethod.POST, request, OwnerResponseDto.class);
    }

    public static ResponseEntity<OwnerResponseDto> update(TestRestTemplate template,
                                                          String rootUrl,
                                                          OwnerRequestDto requestDto,
                                                          Long id) {
        HttpEntity<OwnerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/owners" + "/" + id, HttpMethod.PUT, request, OwnerResponseDto.class);
    }
}
