package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.repository.CertificateRepository;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Created by ZotovES on 19.05.2019
 * Валидация для Программ
 */
@Component
@RequiredArgsConstructor
public class ProgramValidator implements Validator<Program> {
    private CertificateRepository certificateRepository;

    /**
     * Валидирует сужность
     *
     * @param target сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    @Override
    public void validate(Program target, Object hint, Collection<String> errors) {
        if (hint.equals(Delete.class)) {
            certificateRepository.findByProgramId(target.getId()).forEach(getTextErrorCertificate(target, errors));
        }

        if (!errors.isEmpty()) {
            errors.add("Не возможно удалить запись, отредактируйте зависимые записи");
        }
    }

    /**
     * Формирует текст ошибки при наличии связанных сущностей
     *
     * @param target проверяемая сущность
     * @param errors список ошибок
     */
    @NonNull
    private Consumer<Certificate> getTextErrorCertificate(Program target, Collection<String> errors) {
        return certificate -> errors.add(String.format("Программа %s %s используется в сертификате %s %s",
                target.getId(), target.getName(), certificate.getId(), certificate.getName()));
    }
}
