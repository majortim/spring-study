package org.example.security.dto;

import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull(message = "ID를 입력해주세요.")
    private String userId;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
    public LoginRequest() {

    }
    public LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
