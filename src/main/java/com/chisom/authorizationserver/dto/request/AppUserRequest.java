package com.chisom.authorizationserver.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Chisom.Iwowo
 */
@Getter
@Setter
public class AppUserRequest {

    @NotBlank(message = "please enter a valid email")
    @Email(message = "please enter a valid email")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "please enter your full name")
    private String fullName;

    @NotBlank(message = "please enter your password")
    private String password;

    private String mobile;
}
