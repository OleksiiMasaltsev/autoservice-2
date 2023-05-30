package ua.masaltsev.autoservice2.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.masaltsev.autoservice2.dto.request.CarRequestDto;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.CarResponseDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.owner.OwnerClient;

public class CarScenario {
    public static void carCrud(TestRestTemplate template, String rootUrl) {
        CarResponseDto creationResponseDto = createCar(template, rootUrl);
        getCarById(template, rootUrl, creationResponseDto);
        CarResponseDto updateResponseDto = updateCar(template, rootUrl, creationResponseDto);
        deleteCarById(template, rootUrl, updateResponseDto);
    }

    private static void deleteCarById(TestRestTemplate template, String rootUrl, CarResponseDto updateResponseDto) {
        ResponseEntity<CarResponseDto> successfulDeletionResponse =
                CarClient.deleteById(template, rootUrl, updateResponseDto.getId());
        assertEquals(HttpStatus.NO_CONTENT, successfulDeletionResponse.getStatusCode());
    }

    private static CarResponseDto updateCar(TestRestTemplate template,
                                            String rootUrl,
                                            CarResponseDto creationResponseDto) {
        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setYear(Short.valueOf("2023"));
        carRequestDto.setPlate("122445");
        carRequestDto.setBrand("Mercedes");
        carRequestDto.setModel("DSL");
        carRequestDto.setOwnerId(creationResponseDto.getOwnerId());
        ResponseEntity<CarResponseDto> updateResponse =
                CarClient.update(template, rootUrl, carRequestDto, creationResponseDto.getId());

        CarResponseDto updateResponseBody = updateResponse.getBody();
        assertNotNull(updateResponseBody);
        assertEquals(creationResponseDto.getId(), updateResponseBody.getId());
        assertEquals(carRequestDto.getYear(), updateResponseBody.getYear());
        assertEquals(carRequestDto.getPlate(), updateResponseBody.getPlate());
        assertEquals(carRequestDto.getBrand(), updateResponseBody.getBrand());
        assertEquals(carRequestDto.getModel(), updateResponseBody.getModel());
        assertEquals(carRequestDto.getOwnerId(), updateResponseBody.getOwnerId());
        return updateResponseBody;
    }

    private static void getCarById(TestRestTemplate template, String rootUrl, CarResponseDto responseDto) {
        ResponseEntity<CarResponseDto> response = CarClient.getById(template, rootUrl, responseDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CarResponseDto carResponse = response.getBody();
        assertNotNull(carResponse);
        assertEquals(responseDto.getId(), carResponse.getId());
        assertEquals(responseDto.getYear(), carResponse.getYear());
        assertEquals(responseDto.getPlate(), carResponse.getPlate());
        assertEquals(responseDto.getModel(), carResponse.getModel());
        assertEquals(responseDto.getBrand(), carResponse.getBrand());
    }

    private static CarResponseDto createCar(TestRestTemplate template, String rootUrl) {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oles");
        ownerRequestDto.setCarIds(Collections.emptyList());
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        ResponseEntity<OwnerResponseDto> ownerCreationResponse =
                OwnerClient.save(template, rootUrl, ownerRequestDto);
        assertNotNull(ownerCreationResponse.getBody());

        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setOwnerId(ownerCreationResponse.getBody().getId());
        carRequestDto.setYear(Short.valueOf("2021"));
        carRequestDto.setPlate("456598");
        carRequestDto.setBrand("Dacia");
        carRequestDto.setModel("Duster");
        ResponseEntity<CarResponseDto> carCreationResponse =
                CarClient.save(template, rootUrl, carRequestDto);

        assertEquals(HttpStatus.CREATED, carCreationResponse.getStatusCode());
        CarResponseDto carResponseDto = carCreationResponse.getBody();
        assertNotNull(carResponseDto);
        assertNotNull(carResponseDto.getId());
        assertEquals(carRequestDto.getYear(), carResponseDto.getYear());
        assertEquals(carRequestDto.getBrand(), carResponseDto.getBrand());
        assertEquals(carRequestDto.getModel(), carResponseDto.getModel());
        assertEquals(carRequestDto.getPlate(), carResponseDto.getPlate());
        return carResponseDto;
    }
}
