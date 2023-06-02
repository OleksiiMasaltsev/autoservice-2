package ua.masaltsev.autoservice2.worker;

import java.math.BigDecimal;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.WorkerRequestDto;
import ua.masaltsev.autoservice2.dto.response.WorkerResponseDto;

public class WorkerClient {

    private static final String WORKERS_ENDPOINT = "/workers";

    public static ResponseEntity<WorkerResponseDto> save(TestRestTemplate template,
                                                         String rootUrl,
                                                         WorkerRequestDto requestDto) {
        HttpEntity<WorkerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + WORKERS_ENDPOINT, HttpMethod.POST, request, WorkerResponseDto.class);
    }

    public static ResponseEntity<BigDecimal> getSalary(TestRestTemplate template, String rootUrl, Long id) {
        return template.exchange(rootUrl + WORKERS_ENDPOINT + "/" + id + "/salary",
                HttpMethod.GET, null, BigDecimal.class);
    }
}
