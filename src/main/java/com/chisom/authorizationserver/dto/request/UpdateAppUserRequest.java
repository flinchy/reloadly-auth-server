package com.chisom.authorizationserver.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UpdateAppUserRequest {
    @NotBlank(message = "please enter a valid email")
    @Email(message = "please enter a valid email")
    private String username;

    @NotBlank(message = "please enter your full name")
    private String fullName;

    @NotBlank(message = "please enter your mobile")
    private String mobile;
}
