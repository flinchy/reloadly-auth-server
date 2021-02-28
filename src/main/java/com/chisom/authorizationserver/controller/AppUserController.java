package com.chisom.authorizationserver.controller;

import com.chisom.authorizationserver.dto.request.AppUserRequest;
import com.chisom.authorizationserver.dto.request.UpdateAppUserRequest;
import com.chisom.authorizationserver.dto.response.AppUserResponse;
import com.chisom.authorizationserver.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Chisom.Iwowo
 */
@RestController
public interface AppUserController {

    /**
     * register user with auth server.
     *
     * @param appUserRequest app user request
     * @return Object
     */
    @PostMapping("oauth/users")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<AppUserResponse> register(@Valid @RequestBody AppUserRequest appUserRequest);

    /**
     * @param updateAppUserRequest update user request
     * @param userId               user id
     * @return Object
     */
    @PostMapping("oauth/update-user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<AppUserResponse> updateAppUser(
            @Valid @RequestBody UpdateAppUserRequest updateAppUserRequest, @PathVariable Long userId);
}