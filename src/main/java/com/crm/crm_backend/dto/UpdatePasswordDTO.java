package com.crm.crm_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
