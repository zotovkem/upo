package ru.utelksp.upo.domain;

import lombok.*;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.service.impl.CrudOperationListener;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Created by ZotovES on 23.03.2019
 * Программное обеспечение
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(CrudOperationListener.class)
@EqualsAndHashCode(exclude = {"orders", "computers", "typeUsing"})
@ToString(exclude = {"orders", "computers", "typeUsing"})
@Table(name = "program", schema = "upo")
public class Program {
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
     * Вид использования
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_using_id", referencedColumnName = "id" )
    private TypeUsing typeUsing;

    /**
     * Список приказов
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mtm_program_order", schema = "upo",
            joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private Set<Order> orders;

    /**
     * Список компьютеров
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "s_ref_program_computer", schema = "upo",
            joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "computer_id")})
    private Set<Computer> computers;

    /**
     * Лицензия
     */
    @Column(name = "license")
    private String license;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
}
