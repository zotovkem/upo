package ru.utelksp.upo.domain.security;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Created by ZotovES on 31.03.2019
 * Роли
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@Table(name = "role", schema = "upo")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
