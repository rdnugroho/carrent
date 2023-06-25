package id.co.indivara.jdt12.miniproject.utilize.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse extends ResponseMessage{
    private List<String> error;

    public ErrorResponse(HttpStatus status, String message, String error){
        super(status,message);
        this.error = Collections.singletonList(error);
    }
    public ErrorResponse(HttpStatus status, String message, List<String> error){
        super(status,message);
        this.error = error;
    }
}
