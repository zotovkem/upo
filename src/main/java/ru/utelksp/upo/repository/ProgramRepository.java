package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.Program;

/**
 * @author Created by ZotovES on 23.03.2019
 * Репозиторй для управления программами
 */
@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
