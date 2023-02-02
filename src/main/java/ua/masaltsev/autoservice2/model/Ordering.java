package ua.masaltsev.autoservice2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;

public class Ordering {
    private Car car;
    private String description;
    private LocalDateTime receivingTime;
    private LocalDateTime completionTime;
    private List<Favor> favors;
    private List<Product> products;
    private OrderingStatus status;
    private BigDecimal price;
}
