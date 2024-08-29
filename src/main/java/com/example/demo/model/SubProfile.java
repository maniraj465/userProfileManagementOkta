package com.example.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubProfile {

@NotEmpty(message="{firstNameNotNullError}")
@Pattern(regexp = "^[A-Za-z]+$", message="{firstNamePatternError}")
private String firstName;

@NotEmpty(message="{emailNotNullError}")
@Email(message="{emailPatternError}")
private String email;

// @NotEmpty(message="{mobilePhoneNotNullError}")
@Pattern(regexp = "^[0-9]+$", message="{mobilePhonePatternError}")
private String mobilePhone;

}