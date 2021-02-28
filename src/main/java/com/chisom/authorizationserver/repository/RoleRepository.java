package com.chisom.authorizationserver.repository;

import com.chisom.authorizationserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chisom.Iwowo
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * find role containing the role name given.
     *
     * @param roleName role name
     * @return role
     */
    Role findByRoleNameContaining(String roleName);
}
