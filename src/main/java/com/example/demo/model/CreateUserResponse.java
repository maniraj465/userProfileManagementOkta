package com.example.demo.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

private String id;
private String status;
private Timestamp created;
private Timestamp activated;
private Timestamp statusChanged;
private Timestamp lastLogin;
private Timestamp lastUpdated;
private Timestamp passwordChanged;
private Profile profile;
private Credentials credentials;
}