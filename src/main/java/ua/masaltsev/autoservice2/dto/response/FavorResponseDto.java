package ua.masaltsev.autoservice2.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FavorResponseDto {
    private Long id;
    private Long orderingId;
    private Long workerId;
    private BigDecimal price;
    private String status;
}
