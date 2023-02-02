package ua.masaltsev.autoservice2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_generator")
    @SequenceGenerator(name = "owner_generator", sequenceName = "owner_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(mappedBy = "owner")
    private List<Car> cars;
    @OneToMany
    private List<Ordering> orderings;
}
