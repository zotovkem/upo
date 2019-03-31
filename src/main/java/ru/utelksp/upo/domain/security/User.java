package ru.utelksp.upo.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Created by ZotovES on 31.03.2019
 * Пользователи
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Table(name = "user", schema = "upo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany
    @JoinTable(name = "mtm_user_role", schema = "upo",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
