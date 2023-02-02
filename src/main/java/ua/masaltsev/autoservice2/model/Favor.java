package ua.masaltsev.autoservice2.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ua.masaltsev.autoservice2.model.status.FavorStatus;

@Entity
@Table(name = "favors")
@Getter
@Setter
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favor_generator")
    @SequenceGenerator(name = "favor_generator", sequenceName = "favor_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordering_id")
    private Ordering ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;
    private BigDecimal price;
    private FavorStatus status;
}
