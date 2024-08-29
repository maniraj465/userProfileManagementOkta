package com.example.demo.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputValidationError {

private int errorCode;
private String error;
private List<String> fieldErrors = new ArrayList<>();
}
