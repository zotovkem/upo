package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utelksp.upo.domain.security.Role;

/**
 * @author Created by ZotovES on 31.03.2019
 * Репозиторий управления ролями.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
