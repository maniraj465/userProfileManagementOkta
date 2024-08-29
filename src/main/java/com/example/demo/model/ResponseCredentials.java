package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCredentials {

private Password password;
private RecoveryQuestion recovery_question;
private Provider provider;
}