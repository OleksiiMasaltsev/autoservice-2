package ua.masaltsev.autoservice2.dto.response;

import lombok.Data;

@Data
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private Short year;
    private String plate;
}
