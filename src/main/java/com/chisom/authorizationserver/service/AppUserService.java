package com.chisom.authorizationserver.service;

import com.chisom.authorizationserver.dto.request.AppUserRequest;
import com.chisom.authorizationserver.dto.request.UpdateAppUserRequest;
import com.chisom.authorizationserver.dto.response.AppUserResponse;
import com.chisom.authorizationserver.dto.response.ApiResponse;

/**
 * @author Chisom.Iwowo
 */
public interface AppUserService {

    /**
     * register new app user
     *
     * @param appUserRequest user request
     */
    ApiResponse<AppUserResponse> registerUser(final AppUserRequest appUserRequest);

    /**
     * update user details.
     *
     * @param updateAppUserRequest update request
     * @return Object.
     */
    ApiResponse<AppUserResponse> updateAppUserRecords(
            final UpdateAppUserRequest updateAppUserRequest, final Long userId);
}
