package com.web.recruitment.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private int id;
    private String username;
    private String email;
    private List<String> roles;
}
