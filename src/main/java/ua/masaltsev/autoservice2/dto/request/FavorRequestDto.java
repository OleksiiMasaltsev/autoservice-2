package ua.masaltsev.autoservice2.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FavorRequestDto {
    private Long orderingId;
    private Long workerId;
    private BigDecimal price;
    private String status;
}
