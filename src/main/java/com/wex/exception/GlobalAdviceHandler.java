package com.wex.exception;

import com.wex.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ResourceBundle;

@RestControllerAdvice
public class GlobalAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoResultException.class)
    public ErrorResponse handleNoResultException(NoResultException e, HttpServletRequest request) {
        var message = ResourceBundle.getBundle("messages", request.getLocale()).getString("no_result");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FAILED_DEPENDENCY, message);
        return ErrorResponse.builder(e, problemDetail).build();
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ErrorResponse purchaseNotFoundException(PurchaseNotFoundException e, HttpServletRequest request) {
        var message = ResourceBundle.getBundle("messages", request.getLocale()).getString("purchase_not_found");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        return ErrorResponse.builder(e, problemDetail).build();
    }

    @ExceptionHandler(InvalidCountryException.class)
    public ErrorResponse invalidCountryException(InvalidCountryException e, HttpServletRequest request) {
        var message = ResourceBundle.getBundle("messages", request.getLocale()).getString("invalid_country");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        return ErrorResponse.builder(e, problemDetail).build();
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ErrorResponse invalidCurrencyException(InvalidCurrencyException e, HttpServletRequest request) {
        var message = ResourceBundle.getBundle("messages", request.getLocale()).getString("invalid_currency");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        return ErrorResponse.builder(e, problemDetail).build();
    }

    @ExceptionHandler(InvalidLocaleException.class)
    public ErrorResponse invalidLocaleException(InvalidLocaleException e, HttpServletRequest request) {
        var message = ResourceBundle.getBundle("messages", request.getLocale()).getString("invalid_locale");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        return ErrorResponse.builder(e, problemDetail).build();
    }
}
