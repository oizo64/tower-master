package ppl.pl.tower.domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ppl.pl.tower.domain.DTO.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AircraftNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerAircraftException(final AircraftNotFoundException ex){
        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .withTitle("Invalid Aircraft")
                        .withDetails(ex.getMessage())
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withErrorType(AircraftNotFoundException.class.getSimpleName())
                        .withErrorCode("Aircraft:001")
                        .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(StringToLongException.class)
    public ResponseEntity<ErrorDTO> handlerStringException(final StringToLongException ex){
        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .withTitle("Invalid string input")
                        .withDetails(ex.getMessage())
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withErrorType(StringToLongException.class.getSimpleName())
                        .withErrorCode("String:001")
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SearchCriteriaNotMatch.class)
    public ResponseEntity<ErrorDTO> handlerSearchCriteriaNotMatchException(final SearchCriteriaNotMatch ex){
        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .withTitle("Invalid search criteria input")
                        .withDetails(ex.getMessage())
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withErrorType(SearchCriteriaNotMatch.class.getSimpleName())
                        .withErrorCode("SearchCriteria:001")
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
