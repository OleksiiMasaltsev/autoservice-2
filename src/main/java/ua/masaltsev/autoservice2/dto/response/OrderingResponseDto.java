package ua.masaltsev.autoservice2.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderingResponseDto {
    private Long id;
    private String description;
    private LocalDateTime receivingTime;
    private LocalDateTime completionTime;
    private String status;
    private BigDecimal price;
}
