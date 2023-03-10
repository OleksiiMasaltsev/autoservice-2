package ua.masaltsev.autoservice2.dto.request;

import java.math.BigDecimal;
import lombok.Data;
import ua.masaltsev.autoservice2.model.status.FavorStatus;

@Data
public class FavorRequestDto {
    private Long orderingId;
    private Long workerId;
    private BigDecimal price;
    private FavorStatus status;
}
