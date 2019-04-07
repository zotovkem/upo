package ru.utelksp.upo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utelksp.upo.domain.dictionary.TypeEvent;
import ru.utelksp.upo.domain.security.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Created by ZotovES on 07.04.2019
 * Сущность события в журнале логирования
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journal_event", schema = "upo")
public class JournalEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата события
     */
    @Column(name = "date_event")
    private LocalDate dateEvent;

    /**
     * Время события
     */
    @Column(name = "time_event")
    private LocalTime timeEvent;

    /**
     * Пользователь совершивший операцию
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Тип события
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_event_id", referencedColumnName = "id")
    private TypeEvent typeEvent;

    /**
     * Наименование ресурса
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание события
     */
    @Column(name = "description")
    private String stackTrace;
}
