package ua.masaltsev.autoservice2.car;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;

public class CarClient {
    public static ResponseEntity<CarResponseDto> save(TestRestTemplate template,
                                                      String rootUrl,
                                                      CarRequestDto requestDto) {
        HttpEntity<CarRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/cars", HttpMethod.POST, request, CarResponseDto.class);
    }
}
