package ua.masaltsev.autoservice2.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;

@Entity
@Table(name = "orderings")
@Getter
@Setter
public class Ordering {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
    private String description;
    @Column(name = "receiving_time")
    private LocalDateTime receivingTime;
    @Column(name = "completion_time")
    private LocalDateTime completionTime;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "ordering")
    private Set<Favor> favors;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordering_id")
    private Set<Product> products;
    @Enumerated(EnumType.STRING)
    private OrderingStatus status;
    private BigDecimal price;
}
