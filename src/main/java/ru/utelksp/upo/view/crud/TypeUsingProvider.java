package ru.utelksp.upo.view.crud;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

import java.util.stream.Stream;

/**
 * @author Created by ZotovES on 27.03.2019
 */
public class TypeUsingProvider extends AbstractBackEndDataProvider {

    @Override
    protected Stream fetchFromBackEnd(Query query) {
        return null;
    }

    @Override
    protected int sizeInBackEnd(Query query) {
        return 0;
    }
}
