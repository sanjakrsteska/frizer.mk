package mk.frizer.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler(UserNotFoundException.class)
    public static ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(SalonNotFoundException.class)
    public static ResponseEntity<Object> handleSalonNotFound(SalonNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(AppointmentNotFoundException.class)
    public static ResponseEntity<Object> handleAppointmentNotFound(AppointmentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotDivisibleBy20Minutes.class)
    public static ResponseEntity<Object> handleAppointmentNotDivisibleBy20(AppointmentNotDivisibleBy20Minutes e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentsException.class)
    public static ResponseEntity<Object> handleInvalidArguments(InvalidArgumentsException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public static ResponseEntity<Object> handleInvalidUsernameOrPassword(InvalidUsernameOrPasswordException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public static ResponseEntity<Object> handlePasswordsDoNotMatch(PasswordsDoNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public static ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }


    @ExceptionHandler(CityNotFoundException.class)
    public static ResponseEntity<Object> handleCityNotFound(CityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public static ResponseEntity<Object> handleCustomerNotFound(CustomerNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public static ResponseEntity<Object> handleEmailNotFound(EmailNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(ReviewNotFoundException.class)
    public static ResponseEntity<Object> handleReviewNotFound(ReviewNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(SalonTreatmentNotFoundException.class)
    public static ResponseEntity<Object> handleSalonTreatmentNotFound(SalonTreatmentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(TagNotFoundException.class)
    public static ResponseEntity<Object> handleTagNotFound(TagNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(TreatmentNotFoundException.class)
    public static ResponseEntity<Object> handleTreatmentNotFound(TreatmentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
    @ExceptionHandler(UserCannotBeCreatedException.class)
    public static ResponseEntity<Object> handleUserCannotBeCreated(UserCannotBeCreatedException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public static ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public static ResponseEntity<String> handleAuthException(AuthenticationException e){
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }


}