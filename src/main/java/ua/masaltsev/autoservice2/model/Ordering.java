package ua.masaltsev.autoservice2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Car car;
    private String description;
    @Column(name = "receiving_time")
    private LocalDateTime receivingTime;
    @Column(name = "completion_time")
    private LocalDateTime completionTime;
    @OneToMany(mappedBy = "ordering")
    private Set<Favor> favors;
    @OneToMany
    @JoinColumn(name = "ordering_id")
    private Set<Product> products;
    @Enumerated(EnumType.STRING)
    private OrderingStatus status;
    private BigDecimal price;
}
