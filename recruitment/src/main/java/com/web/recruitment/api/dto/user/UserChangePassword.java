package com.web.recruitment.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePassword {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
