package ru.utelksp.upo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Created by ZotovES on 01.04.2019
 * DTO для формировани яотчета по сертификатам
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateReportDto {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Пользователь
     */
    private String employeeFio;
    /**
     * Наименоваиние
     */
    private String name;

    /**
     * Дата окончания
     */
    private LocalDate dateEnd;

    /**
     * Место установки
     */
    private String computer;

    /**
     * Имя ключевого контейнера
     */
    private String keyContainerName;

    /**
     * Программы
     */
    private String program;


}
