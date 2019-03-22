package ru.utelksp.upo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Created by ZotovES on 22.03.2019
 * Сeртификаты
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Дата окончания
     */
    @Column(name = "date_end")
    private ZonedDateTime dateEnd;

    /**
     * Компьютер
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    private Computer computer;
    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
}
