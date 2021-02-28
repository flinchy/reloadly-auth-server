package com.chisom.authorizationserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Chisom.Iwowo
 */
@Getter
@Setter
@Entity
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "please enter a valid email")
    @Email(message = "please enter a valid email")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "please enter your name")
    private String fullName;

    @NotBlank(message = "please enter your password")
    private String password;

    private String mobile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;
}
