package com.example.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile{

@NotEmpty(message="{firstNameNotNullError}")
@Pattern(regexp = "^[A-Za-z]+$", message="{firstNamePatternError}")
private String firstName;

@NotEmpty(message="{lastNameNotNullError}")
@Pattern(regexp = "^[A-Za-z]+$", message="{lastNamePatternError}")
private String lastName;

@NotEmpty(message="{emailNotNullError}")
@Email(message="{emailPatternError}")
private String email;

//@Size(min=12, message="Mobile number should have exactly 10 digits")
//@NotEmpty(message="{mobilePhoneNotNullError}")
@Pattern(regexp = "^[0-9]+$", message="{mobilePhonePatternError}")
private String mobilePhone;

@NotEmpty(message="{loginNotNullError}")
@Email(message="{loginPatternError}")
private String login;

}