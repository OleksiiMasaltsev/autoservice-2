package ua.masaltsev.autoservice2.scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.client.OwnerClient;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;

public class OwnerScenario {
    public static void ownerCrud(TestRestTemplate template, String rootUrl) {
        OwnerResponseDto creationResponseDto = createOwner(template, rootUrl);
        getOwnerById(template, rootUrl, creationResponseDto);
        OwnerResponseDto updateResponseDto = updateOwner(template, rootUrl, creationResponseDto);
        deleteOwnerById(template, rootUrl, updateResponseDto);
    }

    private static void deleteOwnerById(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        ResponseEntity<OwnerResponseDto> successDeletionResponse =
                OwnerClient.deleteById(template, rootUrl, responseDto.getId());
        assertEquals(HttpStatus.NO_CONTENT, successDeletionResponse.getStatusCode());

        ResponseEntity<OwnerResponseDto> absentDeletionResponse =
                OwnerClient.deleteById(template, rootUrl, responseDto.getId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, absentDeletionResponse.getStatusCode());
    }

    private static OwnerResponseDto updateOwner(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        OwnerRequestDto updateRequestDto = new OwnerRequestDto();
        updateRequestDto.setName("Stepan");
        updateRequestDto.setCarIds(Collections.emptyList());
        updateRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> updateResponse =
                OwnerClient.update(template, rootUrl, updateRequestDto, responseDto.getId());

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(updateRequestDto.getName(), updateResponse.getBody().getName());
        return updateResponse.getBody();
    }

    private static void getOwnerById(TestRestTemplate template, String rootUrl, OwnerResponseDto responseDto) {
        ResponseEntity<OwnerResponseDto> response = OwnerClient.getById(template, rootUrl, responseDto.getId());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getId());
        assertEquals(responseDto.getName(), response.getBody().getName());
    }

    private static OwnerResponseDto createOwner(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto creationRequestDto = new OwnerRequestDto();
        creationRequestDto.setName("Oleksii");
        creationRequestDto.setCarIds(Collections.emptyList());
        creationRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> response =
                OwnerClient.save(template, rootUrl, creationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(creationRequestDto.getName(), response.getBody().getName());
        return response.getBody();
    }
}
