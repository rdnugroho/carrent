package id.co.indivara.jdt12.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @Id
    private Long id;
    @Size(max = 50, message = "nomor nik maksimal 50")
    @Column(unique = true)
    private String nik;

    @NotBlank(message = "Nama tidak boleh kosong, null atau hanya spasi kosong")
    @Size(min = 4,max = 50, message = "nama minimal 4 dan maksimal 50")
    @Column(length = 50)
    private String name;

    @Size(max = 50, message = "alamat maksimal 50")
    private String address;

    @NotBlank(message = "email tidak boleh kosong, null atau hanya spasi kosong")
    @Email(message = "Harus berupa email")
    @Size(min = 10,max = 50, message = "email minimal 10 dan maksimal 50")
    @Column(unique = true,length = 50)
    private String email;

    @NotBlank(message = "nomor telepon tidak boleh kosong, null atau hanya spasi kosong")
    @Size(min = 12,max = 15, message = "nomor telepon minimal 12 dan maksimal 15")
    @Column(unique = true, length = 15)
    private String phoneNumber;

}
