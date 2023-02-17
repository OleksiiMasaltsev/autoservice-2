package ua.masaltsev.autoservice2.dto.request;

import java.util.Set;
import lombok.Data;

@Data
public class WorkerRequestDto {
    private String name;
    private Set<Long> orderingIds;
}
