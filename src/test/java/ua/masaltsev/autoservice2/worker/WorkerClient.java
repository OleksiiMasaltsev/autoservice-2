package ua.masaltsev.autoservice2.worker;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.WorkerRequestDto;
import ua.masaltsev.autoservice2.dto.response.WorkerResponseDto;

public class WorkerClient {
    public static ResponseEntity<WorkerResponseDto> save(TestRestTemplate template,
                                                         String rootUrl,
                                                         WorkerRequestDto requestDto) {
        HttpEntity<WorkerRequestDto> request = new HttpEntity<>(requestDto);
        return template.exchange(rootUrl + "/workers", HttpMethod.POST, request, WorkerResponseDto.class);
    }
}
