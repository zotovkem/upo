package ru.utelksp.upo.domain.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utelksp.upo.service.impl.CrudOperationListener;

import javax.persistence.*;

/**
 * Created by ZotovES on 18.03.2019
 * Справочник организаций
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(CrudOperationListener.class)
@Table(name = "s_organization", schema = "upo")
public class Organization {

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
     * Описание
     */
    @Column(name = "description")
    private String description;
}
