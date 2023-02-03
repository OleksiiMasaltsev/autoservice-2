package ua.masaltsev.autoservice2.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FavorResponseDto {
    private Long id;
    private Long orderingId;
    private Long workerId;
    private BigDecimal price;
    private String status;
}
