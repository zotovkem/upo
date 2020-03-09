package ru.utelksp.upo.domain.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utelksp.upo.service.impl.CrudOperationListener;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 07.04.2019
 * Справочник типов событий
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(CrudOperationListener.class)
@Table(name = "s_type_event", schema = "upo")
public class TypeEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование
     */
    @Column(name = "name")
    private String name;

}
