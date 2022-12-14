package sit.int221.oasip.Error;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("TIMESTAMP", Instant.now());
        errors.put("status", status.value());
        errors.put("error", "Validate failed");
        Map<String, String> errorField = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorField.put(fieldName, message);
        });
        errors.put("message",errorField);
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> ExceptionError(HttpStatus status, String message) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("TIMESTAMP", Instant.now());
        errors.put("status", status.value());
        errors.put("message", message);

        return new ResponseEntity<Object>(errors, status);
    }
}