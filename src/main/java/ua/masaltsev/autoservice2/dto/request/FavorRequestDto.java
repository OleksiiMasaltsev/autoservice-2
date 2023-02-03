package ua.masaltsev.autoservice2.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FavorRequestDto {
    private Long orderingId;
    private Long workerId;
    private BigDecimal price;
    private String status;
}
