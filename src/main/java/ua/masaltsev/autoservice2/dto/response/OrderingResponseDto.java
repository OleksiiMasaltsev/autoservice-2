package ua.masaltsev.autoservice2.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderingResponseDto {
    private Long id;
    private Long carId;
    private String description;
    private LocalDateTime receivingTime;
    private LocalDateTime completionTime;
    private List<Long> favorIds;
    private List<Long> productIds;
    private String status;
    private BigDecimal price;
}