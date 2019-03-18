package ru.utelksp.upo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by ZotovES on 18.03.2019
 * Справочник компьютеров
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_computer", schema = "upo")
public class Computer {

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
     *
     */
    @Column(name = "description")
    private String description;
}
