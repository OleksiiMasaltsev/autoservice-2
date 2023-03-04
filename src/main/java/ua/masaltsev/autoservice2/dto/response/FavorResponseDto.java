package ua.masaltsev.autoservice2.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FavorResponseDto {
    private Long id;
    private BigDecimal price;
    private String status;
}
