package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.repository.ProgramRepository;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Created by ZotovES on 14.04.2019
 * Валидатор видов использования ПО
 */

@Component
@RequiredArgsConstructor
public class TypeUsingValidator implements Validator<TypeUsing> {

    private final ProgramRepository programRepository;

    /**
     * Метод валидирующий сущность
     *
     * @param target сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    @Override
    public void validate(TypeUsing target, Object hint, Collection<String> errors) {
        if (hint.equals(Delete.class)) {
            programRepository.findByTypeUsingId(target.getId()).forEach(getTextErrorConsumer(target, errors));
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
    @NotNull
    private Consumer<Program> getTextErrorConsumer(TypeUsing target, Collection<String> errors) {
        return program -> errors.add(String.format("Вид использования %s %s используется в ПО %s %s",
                target.getId(), target.getName(), program.getId(), program.getName()));
    }
}
