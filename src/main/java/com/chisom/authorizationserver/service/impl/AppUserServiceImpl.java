package com.chisom.authorizationserver.service.impl;

import com.chisom.authorizationserver.dto.request.AppUserRequest;
import com.chisom.authorizationserver.dto.request.UpdateAppUserRequest;
import com.chisom.authorizationserver.dto.response.AppUserResponse;
import com.chisom.authorizationserver.dto.response.ApiResponse;
import com.chisom.authorizationserver.exception.CustomException;
import com.chisom.authorizationserver.model.AppUser;
import com.chisom.authorizationserver.repository.AppUserRepository;
import com.chisom.authorizationserver.repository.RoleRepository;
import com.chisom.authorizationserver.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.chisom.authorizationserver.constant.AppConstant.USER;

/**
 * @author Chisom.Iwowo
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final String authServerHost;

    @Autowired
    public AppUserServiceImpl(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate, RoleRepository roleRepository,
            ModelMapper modelMapper, @Value("${auth-server-health}") String authServerHost) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.authServerHost = authServerHost;
    }

    /**
     * register new app user
     *
     * @param appUserRequest user request
     */
    @Override
    public ApiResponse<AppUserResponse> registerUser(AppUserRequest appUserRequest) {
        try {
            //check if user already exists
            Optional<AppUser> appUser = appUserRepository.findByUsername(
                    appUserRequest.getUsername());
            log.info("retrieving user info from db ::: {}", appUser);
            if (appUser.isPresent()) {
                throw new CustomException("user already exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            AppUser newAppUser = modelMapper.map(appUserRequest, AppUser.class);
            newAppUser.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
            newAppUser.setRoles(Collections.singletonList(roleRepository.findByRoleNameContaining(USER)));

            AppUser savedUser = appUserRepository.save(newAppUser);
            log.info("saving user to db ::: {}", savedUser);

            return new ApiResponse<>(modelMapper.map(savedUser, AppUserResponse.class),
                    "user registered successfully", true);

        } catch (Exception exception) {
            log.error("caught an exception :::", exception);
            return new ApiResponse<>(null, exception.getMessage(), false);
        }
    }

    /**
     * update user details.
     *
     * @param updateAppUserRequest update request
     * @return Object.
     */
    @Override
    public ApiResponse<AppUserResponse> updateAppUserRecords(
            final UpdateAppUserRequest updateAppUserRequest, final Long userId
    ) {
        try {
            Optional<AppUser> appUser = appUserRepository.findById(userId);
            log.info("retrieving user info from db ::: {} ::: user id {} ", appUser, userId);

            if (appUser.isPresent()) {
                AppUser updateUser = saveUpdatedRecord(appUser.get(), updateAppUserRequest);

                return new ApiResponse<>(modelMapper.map(updateUser, AppUserResponse.class),
                        "user updated successfully", true);
            }

            return new ApiResponse<>(null, "user not found", false);

        } catch (Exception exception) {
            throw new CustomException("could not process request", exception, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private AppUser saveUpdatedRecord(
            AppUser appUser, UpdateAppUserRequest updateAppUserRequest
    ) {
        log.info("update app user request ::: {}", updateAppUserRequest);
        appUser.setUsername(updateAppUserRequest.getUsername());
        appUser.setFullName(updateAppUserRequest.getFullName());
        appUser.setMobile(updateAppUserRequest.getMobile());

        appUserRepository.save(appUser);
        log.info("saving updated user record to db ::: {}", appUser);

        return appUser;
    }

    /**
     * ping url every 5min to keep alive
     */
    @Async
    @Scheduled(fixedRate = 300000)
    public void health() {
        try {
            CompletableFuture.runAsync(() ->
                    restTemplate.getForObject(authServerHost, Object.class));
        } catch (Exception e) {
            log.error("caught an exception :::", e);
        }
    }
}
