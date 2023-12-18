package com.eazyBank.loan.exception;

import com.eazyBank.loan.dtos.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * This will handler will handel all the Run time exception which hasn't been caught explicitly
     * @param exception,webRequest the exception and web request to get the API path
     * @return Response entity with Error dto body
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> ExceptionHandler(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(LoanAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> LoanAlreadyExistExceptionHandler(WebRequest webRequest, LoanAlreadyExistException exception){
        return new ResponseEntity<>(new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,exception.getMessage(), LocalDateTime.now()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> ResourceNotFoundExceptionHandler(WebRequest webRequest,ResourceNotFoundException exception){
        return new ResponseEntity<>(new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.NOT_FOUND,exception.getMessage(),LocalDateTime.now()),HttpStatus.NOT_FOUND);
    }



    /**
     * This handler is used to handel the jakarta.validation exception
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        List<ObjectError> validationErrorList=ex.getBindingResult().getAllErrors();
        Map<String,String> validationErrorMap=new HashMap<>();
        validationErrorList.forEach(error->validationErrorMap.put(((FieldError)error).getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(validationErrorMap,HttpStatus.BAD_REQUEST);
    }
}
