package ua.masaltsev.autoservice2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workers")
@Getter
@Setter
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worker_generator")
    @SequenceGenerator(name = "worker_generator", sequenceName = "worker_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "workers_orderings",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "ordering_id"))
    private Set<Ordering> orderings;
}
