package com.example.demo.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

private String id;
private String status;
private Timestamp created;
private Timestamp activated;
private Timestamp statusChanged;
private Timestamp lastLogin;
private Timestamp lastUpdated;
private Timestamp passwordChanged;
private Profile profile;
private ResponseCredentials credentials;
}