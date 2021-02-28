package com.chisom.authorizationserver.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Chisom.Iwowo
 */
@Getter
@Setter
public class AppUserResponse {
    private Long userId;
    private String username;
    private String fullName;
}
