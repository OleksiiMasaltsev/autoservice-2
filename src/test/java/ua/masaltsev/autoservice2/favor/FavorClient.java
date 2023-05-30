package ua.masaltsev.autoservice2.favor;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;

public class FavorClient {
    public static ResponseEntity<FavorResponseDto> save(TestRestTemplate template,
                                                        String rootUrl,
                                                        FavorRequestDto requestDto) {
        HttpEntity<FavorRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/favors", HttpMethod.POST, request, FavorResponseDto.class);
    }
}
