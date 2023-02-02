package ua.masaltsev.autoservice2.dto.request;

import lombok.Data;

@Data
public class CarRequestDto {
    private String brand;
    private String model;
    private Short year;
    private String plate;
    private Long ownerId;
}
