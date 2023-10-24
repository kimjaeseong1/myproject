package com.example.withfuture.exception;

import com.example.withfuture.exception.board.BoardErrorCode;
import com.example.withfuture.exception.board.BoardException;
import com.example.withfuture.exception.member.ErrorCode;
import com.example.withfuture.exception.member.MemberException;
import com.example.withfuture.service.MessageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException ex){
        ErrorCode errorCode = ex.getErrorCode();

        List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        String errorMessage = messageService.getMessage(errorCode.getMessage());
        errors.add(new ErrorResponse.ValidationError(ex.getField(), errorMessage));

        ErrorResponse errorResponse = new ErrorResponse(errorCode.name(),errors);

        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request){
        List<ErrorResponse.ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse.ValidationError(error.getField(), error.getDefaultMessage()))

                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse("ValidationError",errors);

        return ResponseEntity.badRequest().body(errorResponse);
   }

   @ExceptionHandler(BoardException.class)
    public ResponseEntity<ErrorResponse> handleBoardException(BoardException ex
                                                              ){
       BoardErrorCode errorCode = ex.getBoardErrorCode();

        List<ErrorResponse.ValidationError> errors = new ArrayList<>();
//       String errorMessage = messageService.getMessage(errorCode.getMessage());
       String customMessage = ex.getMessage();
       String errorMessage;
       if (customMessage != null && !customMessage.isEmpty()) {
           errorMessage = customMessage;
       } else {
           errorMessage = messageService.getMessage(errorCode.getMessage());
       }

       errors.add(new ErrorResponse.ValidationError("error.getField", errorMessage));

    ErrorResponse errorResponse = new ErrorResponse(errorCode.name(),errors);
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);

   }
}
