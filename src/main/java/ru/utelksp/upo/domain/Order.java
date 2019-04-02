package ru.utelksp.upo.domain;

import lombok.*;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.domain.dictionary.Organization;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Created by ZotovES on 22.03.2019
 * Приказы
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"organization"})
@ToString(exclude = {"organization"})
@Table(name = "account_order", schema = "upo")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер приказа
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * Дата приказа
     */
    @Column(name = "order_date")
    private LocalDate orderDate;

    /**
     * Организация
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    /**
     * Список пользователей
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "s_ref_program_employee", schema = "upo",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private Set<Employee> employees;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
}
