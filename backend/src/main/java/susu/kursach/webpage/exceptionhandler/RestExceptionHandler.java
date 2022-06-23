package susu.kursach.webpage.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleException(Exception ex) {
        log.error(ex.getMessage());
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(),
                ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
