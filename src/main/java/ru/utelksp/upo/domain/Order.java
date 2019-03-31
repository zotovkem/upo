package ru.utelksp.upo.domain;

import lombok.*;
import ru.utelksp.upo.domain.dictionary.Organization;

import javax.persistence.*;
import java.time.LocalDate;

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
     * Описание
     */
    @Column(name = "description")
    private String description;
}
