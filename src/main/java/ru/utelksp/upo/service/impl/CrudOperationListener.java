package ru.utelksp.upo.service.impl;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * @author Created by ZotovES on 07.04.2019
 * Листнер слушает crud события энтити манеджера и записывает в аудит жкрнал.
 */
public class CrudOperationListener {

    @PostPersist
    public void postPersist(Object o) {
    }

    @PostRemove
    public void postRemove(Object o) {
    }

    @PostUpdate
    public void postUpdate(Object o) {
    }
}
