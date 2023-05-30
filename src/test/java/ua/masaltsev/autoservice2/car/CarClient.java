package ua.masaltsev.autoservice2.car;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;

public class CarClient {

    private static final String CARS_ENDPOINT = "/cars";

    public static ResponseEntity<CarResponseDto> save(TestRestTemplate template,
                                                      String rootUrl,
                                                      CarRequestDto requestDto) {
        HttpEntity<CarRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + CARS_ENDPOINT,
                HttpMethod.POST, request, CarResponseDto.class);
    }

    public static ResponseEntity<CarResponseDto> getById(TestRestTemplate template, String rootUrl, Long id) {
        return template.exchange(rootUrl + CARS_ENDPOINT + "/" + id,
                HttpMethod.GET, null, CarResponseDto.class);
    }

    public static ResponseEntity<CarResponseDto> update(TestRestTemplate template,
                                                        String rootUrl,
                                                        CarRequestDto carRequestDto,
                                                        Long id) {
        HttpEntity<CarRequestDto> request = new HttpEntity<>(carRequestDto);
        return template.exchange(rootUrl + CARS_ENDPOINT + "/" + id, HttpMethod.PUT, request, CarResponseDto.class);
    }

    public static ResponseEntity<CarResponseDto> deleteById(TestRestTemplate template, String rootUrl, Long id) {
        return template.exchange(rootUrl + CARS_ENDPOINT + "/" + id,
                HttpMethod.DELETE, null, CarResponseDto.class);
    }
}
