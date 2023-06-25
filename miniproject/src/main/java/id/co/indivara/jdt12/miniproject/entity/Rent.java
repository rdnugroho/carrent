package id.co.indivara.jdt12.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "rent",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Transaction transaction;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "driver")
    private Driver driver;
    private Boolean isCurrentlyRented;
}

