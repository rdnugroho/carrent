package id.co.indivara.jdt12.miniproject.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDriverByLevelAndLowestCountException extends RuntimeException {
    private String message;
}
