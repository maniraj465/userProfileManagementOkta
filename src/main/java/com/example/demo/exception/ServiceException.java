package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceException extends RuntimeException {

private static final long serialVersionUID = 1L;
private String message;
private int errorCode;
}

