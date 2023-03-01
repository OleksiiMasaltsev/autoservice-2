package ua.masaltsev.autoservice2.dto.request;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderingRequestDto {
    private Long carId;
    private String description;
    private LocalDateTime completionTime;
    private Set<Long> favorIds;
    private Set<Long> productIds;
}
