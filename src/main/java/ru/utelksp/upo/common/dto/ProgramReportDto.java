package ru.utelksp.upo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Created by ZotovES on 02.04.2019
 * Дто для отображения приказов в отчете.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramReportDto {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Наименоваиние програмного обеспечения
     */
    private String nameProgram;

    /**
     * Пользователь
     */
    private String employeeFio;

    /**
     * Дата приказа
     */
    private LocalDate orderDate;

    /**
     * Номер приказа
     */
    private String number;
}
