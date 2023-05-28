package ua.masaltsev.autoservice2.owner;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;

public class OwnerClient {

    private static final String OWNERS_ENDPOINT = "/owners";

    public static ResponseEntity<OwnerResponseDto> save(TestRestTemplate template,
                                                        String rootUrl,
                                                        OwnerRequestDto requestDto) {
        HttpEntity<OwnerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + OWNERS_ENDPOINT,
                HttpMethod.POST, request, OwnerResponseDto.class);
    }

    public static ResponseEntity<OwnerResponseDto> update(TestRestTemplate template,
                                                          String rootUrl,
                                                          OwnerRequestDto requestDto,
                                                          Long id) {
        HttpEntity<OwnerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + OWNERS_ENDPOINT + "/" + id,
                HttpMethod.PUT, request, OwnerResponseDto.class);
    }

    public static ResponseEntity<OwnerResponseDto> getById(TestRestTemplate template,
                                                           String rootUrl,
                                                           Long id) {
        return template.exchange(rootUrl + OWNERS_ENDPOINT + "/" + id,
                HttpMethod.GET, null, OwnerResponseDto.class);
    }

    public static ResponseEntity<OwnerResponseDto> deleteById(TestRestTemplate template,
                                                              String rootUrl,
                                                              Long id) {
        return template.exchange(rootUrl + OWNERS_ENDPOINT + "/" + id,
                HttpMethod.DELETE, null, OwnerResponseDto.class);
    }

    public static ResponseEntity<OwnerResponseDto[]> getOrderings(TestRestTemplate template,
                                                                  String rootUrl,
                                                                  Long id) {
        return template.exchange(rootUrl + OWNERS_ENDPOINT + "/" + id + "/orderings",
                HttpMethod.GET, null, OwnerResponseDto[].class);
    }
}
