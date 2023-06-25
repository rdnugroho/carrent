package id.co.indivara.jdt12.miniproject.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "nomor nik maksimal 50")
    @Column(unique = true,length = 50)
    private String nik;

    private Date startWork;

    @Size(max = 50, message = "nomor sim maksimal 50")
    @Column(unique = true, length = 50)

    private String sim;

    @NotBlank(message = "Nama tidak boleh kosong, null atau hanya spasi kosong")
    @Size(min = 4,max = 50, message = "nama minimal 4 dan maksimal 50")
    @Column(length = 50)
    private String name;

    @Size(max = 50, message = "alamat maksimal 50")
    @Column(length = 50)
    private String address;


    @NotBlank(message = "email tidak boleh kosong, null atau hanya spasi kosong")
    @Email(message = "Harus berupa email")
    @Size(min = 10,max = 50, message = "email minimal 10 dan maksimal 50")
    @Column(unique = true,length=50)
    private String email;

    @Column(length = 2)
    private Integer level;

    @NotBlank(message = "nomer HP tidak boleh kosong, null atau hanya spasi kosong")
    @Size(min = 11,max = 15, message = "nomor hp minimal 12 dan maksimal 15")
    @Column(unique = true, length = 15)
    private String phoneNumber;

    private BigDecimal costPerHour;
    private Integer countOfRented;
    private Boolean isAvailable;

}

