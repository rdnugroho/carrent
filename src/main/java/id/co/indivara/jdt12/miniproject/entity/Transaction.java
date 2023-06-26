package id.co.indivara.jdt12.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Table(name = "trx_rentcar")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Rent rent;

    @JsonFormat(pattern = "dd-mm-yyyy HH:mm:ss", timezone = "Asia/Singapore")
    private Date startDate;
    @JsonFormat(pattern = "dd-mm-yyyy HH:mm:ss", timezone = "Asia/Singapore")
    private Date endRent;

    private Integer duration;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "driver_cost")
    private BigDecimal driverCost;

    private Boolean isAlreadyPaid;

}
