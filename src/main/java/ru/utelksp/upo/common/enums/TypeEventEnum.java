package ru.utelksp.upo.common.enums;

import lombok.Getter;

/**
 * @author Created by ZotovES on 07.04.2019
 * Типы событий в журнале
 */
@Getter
public enum TypeEventEnum {
    ADD_ENTITY(1L),
    UPDATE_ENTITY(2L),
    REMOVE_ENTITY(3L),
    LOGIN_AGAIN(4L),
    AUTH_USER(5L),
    APP_ERROR(6L),
    LOGOUT_USER(7L);

    private Long typeEventId;

    TypeEventEnum(Long typeEventId) {
        this.typeEventId = typeEventId;
    }
}
