package com.chisom.authorizationserver.controller;

import com.chisom.authorizationserver.dto.request.AppUserRequest;
import com.chisom.authorizationserver.dto.request.UpdateAppUserRequest;
import com.chisom.authorizationserver.dto.response.ApiResponse;
import com.chisom.authorizationserver.dto.response.AppUserResponse;
import com.chisom.authorizationserver.service.AppUserService;
import org.springframework.stereotype.Service;

/**
 * @author Chisom.Iwowo
 */
@Service
public class AppUserControllerFacade implements AppUserController {

    private final AppUserService appUserService;

    public AppUserControllerFacade(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * register user with auth server.
     *
     * @param appUserRequest app user request
     * @return Object
     */
    @Override
    public ApiResponse<AppUserResponse> register(AppUserRequest appUserRequest) {
        return appUserService.registerUser(appUserRequest);
    }

    /**
     * updates a users record.
     *
     * @param updateAppUserRequest update request
     * @param userId               user id
     * @return Object
     */
    @Override
    public ApiResponse<AppUserResponse> updateAppUser(
             UpdateAppUserRequest updateAppUserRequest, Long userId
    ) {
        return appUserService.updateAppUserRecords(updateAppUserRequest, userId);
    }
}
