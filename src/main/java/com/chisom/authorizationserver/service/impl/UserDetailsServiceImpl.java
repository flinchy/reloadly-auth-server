package com.chisom.authorizationserver.service.impl;

import com.chisom.authorizationserver.exception.CustomException;
import com.chisom.authorizationserver.model.AppUser;
import com.chisom.authorizationserver.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Chisom.Iwowo
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // it will be called at access token generation time
        Optional<AppUser> optUser = appUserRepository.findByUsername(username);
        if (optUser.isPresent()) {
            AppUser user = optUser.get();
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                    .collect(Collectors.toList());
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
        throw new CustomException("user not exist", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
