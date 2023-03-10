package ua.masaltsev.autoservice2.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderingRequestDto {
    private Long carId;
    private String description;
    private LocalDateTime completionTime;
    private List<Long> favorIds;
    private List<Long> productIds;
    private String status;
}
