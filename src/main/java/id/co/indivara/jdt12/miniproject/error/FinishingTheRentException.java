package id.co.indivara.jdt12.miniproject.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishingTheRentException extends RuntimeException{
    private String message;
}
