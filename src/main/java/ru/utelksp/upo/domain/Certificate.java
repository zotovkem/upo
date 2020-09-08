package ru.utelksp.upo.domain;

import lombok.*;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.service.impl.CrudOperationListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Created by ZotovES on 22.03.2019
 * Сeртификаты
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(CrudOperationListener.class)
@EqualsAndHashCode(exclude = {"employee", "computer"})
@ToString(exclude = {"employee", "computer"})
@Table(name = "certificate", schema = "upo")
public class Certificate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование
     */
    @Column(name = "name")
    private String name;

    /**
     * Пользователь
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Дата окончания
     */
    @Column(name = "date_end")
    private LocalDate dateEnd;

    /**
     * Компьютер
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    private Computer computer;

    /**
     * Список программ
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "s_ref_certificate_program", schema = "upo",
            joinColumns = {@JoinColumn(name = "certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "program_id")})
    private Set<Program> programs;

    /**
     * Издатель
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * Имя ключевого контейнера
     */
    @Column(name = "key_container_name")
    private String keyContainerName;

    /**
     * Время окончания действия сертификата
     */
    @Column(name = "time_end")
    private String timeEnd;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
}
