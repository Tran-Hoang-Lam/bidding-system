package info.lamth.biddingsystem.exception.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Builder
public class ErrorResponse {
    private LocalDate timestamp;
    private HttpStatus status;
    private String message;
    private String detailMessage;
}
