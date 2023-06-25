package id.co.indivara.jdt12.miniproject.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama Mobil tidak boleh kosong")
    @Size(max = 50, message = "Nama mobil maksilam 50")
    @Column(length = 50,nullable = false)
    private String name;

    @Max(value = 2500, message = "Tahun tidak boleh lebih dari 5 angka")
    @Column(length = 5,nullable = false)
    private Integer year;

    @Max(value = 20, message = "kursi tidak boleh lebih dari 5 angka")
    @Column(length = 5,nullable = false)
    private Integer seat;

    @Max(value = 10, message = "bagasi tidak boleh lebih dari 5 angka")
    @Column(length = 5,nullable = false)
    private Integer baggage;

    @Column(nullable = false)
    private BigDecimal costPerHour;

    @NotBlank(message = "Nomor Plat tidak boleh kosong")
    @Size(min = 3, max = 15, message = "nomor plat minimal 3 maks 15")
    @Column(unique = true,length = 15,nullable = false)
    private String plate;

    @Column(nullable = false, length = 2)
    private Integer level;

    @Column(nullable = false)

    private Boolean isAvailable;

}
