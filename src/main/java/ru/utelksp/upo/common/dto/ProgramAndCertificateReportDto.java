package ru.utelksp.upo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Created by ZotovES on 19.04.2019
 * DTO для формирования кросс отчета по ПО и сертификатам
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramAndCertificateReportDto {
    /**
     * Идентификатор программы
     */
    private Long id;
    /**
     * Наименоваиние ПО
     */
    private String name;
    /**
     * Идентификатор сертификата
     */
    private Long certificateId;
    /**
     * Наименоваиние сертификата
     */
    private String certificateName;

    /**
     * Дата окончания сертификата
     */
    private LocalDate dateEnd;
}
