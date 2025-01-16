package com.tekarch.AccountServiceMS.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data // Generates getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Generates a no-arguments constructor
@AllArgsConstructor // Generates an all-arguments constructor
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private Boolean twoFactorEnabled = false;
    private String kycStatus = "pending";
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;
}


