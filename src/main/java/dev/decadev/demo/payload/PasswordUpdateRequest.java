package dev.decadev.demo.payload;

import lombok.Data;

@Data
public class PasswordUpdateRequest {
    private String password;
    private String newPassword;
    private String confirmPassword;
}
