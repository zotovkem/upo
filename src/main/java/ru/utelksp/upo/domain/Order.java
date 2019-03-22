package ru.utelksp.upo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utelksp.upo.domain.dictionary.Organization;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Created by ZotovES on 22.03.2019
 * Приказы
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private ZonedDateTime orderDate;

    /**
     * Организация
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
}
